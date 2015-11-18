package com.gmaslowski.dam.camel.iw

import akka.actor.{ActorRef, ActorSystem}
import akka.camel.CamelExtension
import com.gmaslowski.dam.camel.iw.processing.ImageProcessorActor
import org.apache.camel.CamelContext
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ImageWorkerConfiguration {

  @Bean
  def actorSystem(): ActorSystem = {
    ActorSystem("image-worker")
  }

  @Bean
  def camelContext(actorSystem: ActorSystem): CamelContext = {
    CamelExtension(actorSystem).context
  }

  @Bean
  def imageProcessorActor(actorSystem: ActorSystem): ActorRef = {
    actorSystem.actorOf(ImageProcessorActor.props)
  }
}

@SpringBootApplication
object ImageWorker extends App {
  SpringApplication.run(classOf[ImageWorkerConfiguration])
}
