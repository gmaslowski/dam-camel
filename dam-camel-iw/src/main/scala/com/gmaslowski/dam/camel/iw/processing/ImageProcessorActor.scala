package com.gmaslowski.dam.camel.iw.processing

import akka.actor.{Actor, ActorLogging, Stash, Terminated, _}
import akka.pattern.pipe
import com.gmaslowski.dam.camel.iw.processing.ImageProcessorActor.{StartTranscoding, _}
import org.im4java.core.{ConvertCmd, IMOperation}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Failure

object ImageProcessorActor {

  def props = Props(classOf[ImageProcessorActor])

  case class StartTranscoding(requestId: String, fileUrl: String, outputFileUrl: String)

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

}

class ImageProcessorActor extends Actor with ActorLogging with Stash {

  override def receive: Receive = canProcess

  def canProcess: Receive = {
    case StartTranscoding(requestId, fileUrl, outputFileUrl) =>
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
    case StartTranscoding(requestId, fileUrl, outputFileUrl) =>
      stash()
    case msg: Terminated => {
      unstashAll()
      context.become(canProcess, true)
    }
  }

}
