package com.enavrt

import akka.actor.Actor.Receive
import akka.actor.{ActorLogging, Actor, Props, ActorRef}
import akka.event.LoggingReceive

/**
  * Created by wkaminski on 24.02.16.
  */
object EchoActor {
  def props =
    Props(classOf[Client])
}

class EchoActor extends Actor with ActorLogging{
  override def receive: Receive = LoggingReceive{
    case m: String => log.info(s"$m")
  }
}
