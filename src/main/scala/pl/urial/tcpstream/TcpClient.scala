package pl.urial.tcpstream

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import akka.io.{IO, Tcp}

import scala.concurrent.duration._

object TcpClient {
  def props(remote: InetSocketAddress, replies: ActorRef) =
    Props(classOf[TcpClient], remote, replies)
}

class TcpClient(remote: InetSocketAddress, listener: ActorRef) extends Actor with ActorLogging {

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

    case c@Connected(remote, local) =>
      log.info("Connected")
      listener ! c
      val connection = sender()
      connection ! Register(self)
      context become {
        case CommandFailed(w: Write) =>
          // O/S buffer was full
          listener ! "write failed"
        case Received(data) =>
          log.debug(s"received Received(data): $data")
          listener ! data
        case "close" =>
          connection ! Close
        case _: ConnectionClosed =>
          listener ! "connection closed"
          context unbecome()
          connect
      }
  }

  def props(address: String, port: Int, listener: ActorRef): Props = Props(new TcpClient(new InetSocketAddress(address, port), listener))
}