package eu.datacompass.tcpstream

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import akka.io.Tcp._
import akka.util.ByteString

object HandlerProps {
  def props(connection: ActorRef) = Props(classOf[Handler], connection)
}

class Handler(val connection: ActorRef) extends Actor with ActorLogging {


  override def receive: Receive = LoggingReceive{

    case data:ByteString =>{
        connection!Write(data)
    }
    case PeerClosed =>
      log.info("PeerClosed")
      stop()
    case ErrorClosed =>
      log.info("ErrorClosed")
      stop()
    case Closed =>
      log.info("Closed")
      stop()
    case ConfirmedClosed =>
      log.info("ConfirmedClosed")
      stop()
    case Aborted =>
      log.info("Aborted")
      stop()
  }

  def stop() {
    log.info("Stopping")
    context stop self
  }
}