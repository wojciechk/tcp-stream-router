package com.enavrt

import java.net.InetSocketAddress

import akka.actor._


object TCPClientWithAkkaTCP extends App {

  /**
    * Use parameters `127.0.0.1 6001` to start client connecting to
    * server on 127.0.0.1:6001.
    *
    */
  val system = ActorSystem("akka-tcp-system")

  val (address, port) =
    if (args.length == 2) {
      (args(0), args(1).toInt)
    }
    else {
      ("127.0.0.1", 6000)
    }


  val processor = system.actorOf(Props[EchoActor])
  val listener = system.actorOf(TcpListener.props(processor))

  val client=system.actorOf(Client.props(new InetSocketAddress(address,port),listener))

  system.awaitTermination()
}