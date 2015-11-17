package com.gmaslowski.dam.camel.iw

import akka.actor.{ActorRef, ActorSystem}
import akka.camel.CamelExtension
import akka.routing.RoundRobinPool
import com.gmaslowski.dam.camel.iw.processing.{ImageProcessorActor, ImageProcessorProxyActor}
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

  @Bean
  def imageProcessorProxyActor(actorSystem: ActorSystem, imageProcessorActor: ActorRef): ActorRef = {
    actorSystem.actorOf(RoundRobinPool(5).props(ImageProcessorProxyActor.props(imageProcessorActor)))
  }
}

@SpringBootApplication
object ImageWorker extends App {
  SpringApplication.run(classOf[ImageWorkerConfiguration])
}
