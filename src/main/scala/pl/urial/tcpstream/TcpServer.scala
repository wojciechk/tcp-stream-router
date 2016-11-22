package pl.urial.tcpstream

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, Props, Terminated}
import akka.event.LoggingReceive
import akka.io.{IO, Tcp}
import akka.routing.{BroadcastRoutingLogic, Router}
import akka.util.ByteString



object TcpServer {
  def props(host: InetSocketAddress)
  = Props(classOf[TcpServer],host)
}

class TcpServer(host:InetSocketAddress) extends Actor with ActorLogging{
  var router = {
    val routees = Vector()
    Router(BroadcastRoutingLogic(), routees)
  }

  import context.system

  IO(Tcp) ! Tcp.Bind(self, host)

  override def receive : Receive = LoggingReceive {


    case Tcp.Connected(remote, local) =>{
      val handler = context.actorOf(HandlerProps.props(sender))

      context watch handler

      router=router.addRoutee(handler)

      sender ! Tcp.Register(handler)
    }

    case Terminated(actor) => router.removeRoutee(actor)

    case m:ByteString =>
      router.route(m, sender())
  }

}