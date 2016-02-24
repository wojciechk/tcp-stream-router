package com.enavrt

import scala.concurrent.duration._
import akka.actor.{ActorLogging, Actor, ActorRef, Props}
import akka.event.LoggingReceive
import akka.io.{ IO, Tcp }
import akka.util.ByteString
import java.net.InetSocketAddress

object Client {
  def props(remote: InetSocketAddress, replies: ActorRef) =
    Props(classOf[Client], remote, replies)
}

class Client(remote: InetSocketAddress, listener: ActorRef) extends Actor with ActorLogging  {

  import Tcp._
  import context.system



  def connect: Unit = {
    IO(Tcp) ! Connect(remote)
  }

  connect

  override def receive: Receive = LoggingReceive {
    case CommandFailed(_: Connect) =>
      import context.dispatcher

      listener ! "connect failed"

      context.system.scheduler.
        scheduleOnce(2 seconds) {
          connect
        }
//      context stop self

    case c @ Connected(remote, local) =>
      listener ! c
      val connection = sender()
      connection ! Register(self)
      context become {
        case data: ByteString =>
          connection ! Write(data)
        case CommandFailed(w: Write) =>
          // O/S buffer was full
          listener ! "write failed"
        case Received(data) =>
          listener ! data
        case "close" =>
          connection ! Close
        case _: ConnectionClosed =>
          listener ! "connection closed"
          context unbecome()
          connect
      }
  }
  def props(address: String, port: Int, listener: ActorRef): Props = Props(new Client(new InetSocketAddress(address, port), listener))


}