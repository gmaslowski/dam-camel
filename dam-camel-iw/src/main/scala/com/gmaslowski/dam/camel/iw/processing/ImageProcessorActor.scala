package com.gmaslowski.dam.camel.iw.processing

import akka.actor.{Actor, ActorLogging, Stash, Terminated, _}
import akka.camel.{Consumer, CamelMessage}
import akka.pattern.pipe
import com.gmaslowski.dam.camel.iw.processing.ImageProcessorActor._
import org.im4java.core.{ConvertCmd, IMOperation}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Failure

object ImageProcessorActor {

  def props = Props(classOf[ImageProcessorActor])

  case object ProcessingFinished

  def createIMagickOperation(sourceImage: String, targetImage: String): IMOperation = {
    val op: IMOperation = new IMOperation
    op.addImage(sourceImage)
    op.addImage(targetImage)
    op
  }

  def runIMagickOperation(op: IMOperation) = {
    val cmd: ConvertCmd = new ConvertCmd()
    cmd.run(op)
  }

  def resolveExtension(contentType: String): String = contentType match {
    case "image/jpg" => "jpg"
    case "image/jpeg" => "jpg"
    case "image/png" => "png"
    case _ => "unknown"
  }
}

class ImageProcessorActor extends Consumer with ActorLogging with Stash {

  def endpointUri = "file:/tmp/dam-camel-storage/?recursive=true&noop=true&exclude=.*flavor.*"

  override def receive: Receive = canProcess

  def canProcess: Receive = {

    case msg: CamelMessage =>
      val requestId = msg.getHeaderAs("MessageExchangeId", classOf[String], camelContext)
      val fileUrl = msg.getHeaderAs("CamelFilePath", classOf[String], camelContext)
      val contentType = msg.getHeaderAs("CamelFileContentType", classOf[String], camelContext)
      val outputFileUrl = s"""${msg.getHeaderAs("CamelFileParent", classOf[String], camelContext)}/flavor.${resolveExtension(contentType)}"""

      log.info(s"Processing request $requestId (input: $fileUrl).")

      val child = context.actorOf(Props(new Actor {
        implicit val ec: ExecutionContext = context.dispatcher

        Future {
          val operation = ImageProcessorActor.createIMagickOperation(fileUrl, outputFileUrl)
          runIMagickOperation(operation)
          ProcessingFinished
        }(ec).pipeTo(self)

        override def receive: Actor.Receive = {
          case ProcessingFinished =>

            context.stop(self)
            log.info(s"Request $requestId processed successfully (output: $outputFileUrl).")

          case Failure(fail) =>
            context.stop(self)
            log.warning(s"Processing request $requestId failed {}.", fail)
        }
      }))

      context.watch(child)
      context.become(processing, true)
  }

  def processing: Receive = {
    case msg: CamelMessage =>
      stash()
    case msg: Terminated => {
      unstashAll()
      context.become(canProcess, true)
    }
  }

}
