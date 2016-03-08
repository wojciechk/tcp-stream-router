package com.enavrt

import akka.actor.{ActorRef, Props, ActorLogging, Actor}
import akka.event.LoggingReceive
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.io.Framing
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString

/**
  * Created by wkaminski on 13.02.16.
  */
object TcpListener {
  def props(dest:ActorRef) =
    Props(classOf[TcpListener],dest)
}


class TcpListener(dest:ActorRef) extends Actor with ActorLogging {

  implicit val materializer = ActorMaterializer()

  val flow = Source.actorRef(10, OverflowStrategy.fail).via(Framing.delimiter(
    ByteString("\n"),
    maximumFrameLength = 256,
    allowTruncation = false))
    .map{_.utf8String}
    .to(Sink.actorRef(dest,"\t\tCompleted!!"))
    .run()

  override def receive: Receive = LoggingReceive {

    case m: ByteString =>{
      log.debug(s"\t-->${m.utf8String}<--")
      flow ! m
    }

    case s:String=>{
      log.info(s)
    }

  }
  def props(dest:ActorRef): Props = Props(new TcpListener(dest))

}

