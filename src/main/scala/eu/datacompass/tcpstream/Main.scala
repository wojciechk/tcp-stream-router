package eu.datacompass.tcpstream

import java.net.InetSocketAddress

import akka.actor._



object Main extends App {

  val system = ActorSystem("akka-tcp-system")

  if (args.length != 4) {
    println("arguments required: clientHost, clientPort, serverHost,serverPort")
    println("eg `hydra1.datacompass.eu 54046 localhost 6000`")
    System.exit(0)
  }

  val (clientHost, clientPort, serverHost,serverPort) =
      (args(0), args(1).toInt,args(2), args(3).toInt)



  val server=system.actorOf(TcpServer.props(new InetSocketAddress(serverHost, serverPort)))

  val client=system.actorOf(TcpClient.props(new InetSocketAddress(clientHost,clientPort),server))


  system.awaitTermination()
}