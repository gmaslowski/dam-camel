package com.gmaslowski.dam.camel.iw.processing

import akka.actor.{ActorLogging, ActorRef, Props}
import akka.camel.{CamelMessage, Consumer}
import com.gmaslowski.dam.camel.iw.processing.ImageProcessorActor.StartTranscoding
import com.gmaslowski.dam.camel.iw.processing.ImageProcessorProxyActor.resolveExtension

object ImageProcessorProxyActor {

  def props(imageProcessor: ActorRef) = Props(classOf[ImageProcessorProxyActor], imageProcessor)

  def resolveExtension(contentType: String): String = contentType match {
    case "image/jpg" => "jpg"
    case "image/jpeg" => "jpg"
    case "image/png" => "png"
    case _ => "unknown"
  }
}

class ImageProcessorProxyActor(imageProcessor: ActorRef) extends Consumer with ActorLogging {
  def endpointUri = "file:/tmp/dam-camel-storage/?recursive=true&noop=true&exclude=.*flavor.*"

  override def receive = {
    case msg: CamelMessage =>

      val requestId = msg.getHeaderAs("MessageExchangeId", classOf[String], camelContext)
      val sourceUrl = msg.getHeaderAs("CamelFilePath", classOf[String], camelContext)
      val contentType = msg.getHeaderAs("CamelFileContentType", classOf[String], camelContext)
      val destinationUrl = s"""${msg.getHeaderAs("CamelFileParent", classOf[String], camelContext)}/flavor.${resolveExtension(contentType)}"""

      imageProcessor ! StartTranscoding(requestId, sourceUrl, destinationUrl)
  }
}
