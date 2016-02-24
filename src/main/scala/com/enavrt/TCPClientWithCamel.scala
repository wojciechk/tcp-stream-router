package sample.stream

import akka.camel.{CamelExtension, CamelMessage, Consumer}
import akka.actor.{ ActorSystem, Props }
import akka.stream.{ActorMaterializerSettings, ActorMaterializer}

object TCPClientWithCamel extends App{

  /**
    * Use parameters `client 127.0.0.1 6001` to start client connecting to
    * server on 127.0.0.1:6001.
    *
    */


    val (address, port) =
      if (args.length == 2) {
        (args(0), args(1).toInt)
      }
      else {
        ("127.0.0.1", 6000)
      }


    class MyEndpoint extends Consumer {

// nie działa
//      def endpointUri = "mina2:tcp://localhost:6000?textline=true&clientMode=true"

      def endpointUri = "netty4:tcp://localhost:6000?textline=true&clientMode=true&sync=false"

      def receive = {
        case msg: CamelMessage => {
          println("received %s" format msg.bodyAs[String])
        }
      }
    }



    val system = ActorSystem("akka-camel-system")
    val camel = CamelExtension(system)
    val endpoint = system.actorOf(Props[MyEndpoint])



}