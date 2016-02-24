package com.enavrt

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.testkit.{TestActorRef, TestActors, TestKit, ImplicitSender}
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll

class TCPListenerSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("TcpClient"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }


  "A Listener" must {
    "merge lines of Bytestring" in{

      val listener =TestActorRef[TcpListener]

    }

  }


//
//  "A Ping actor" must {
//    "send back a ping on a pong" in {
//      val pingActor = system.actorOf(PingActor.props)
//      pingActor ! PongActor.PongMessage("pong")
//      expectMsg(PingActor.PingMessage("ping"))
//    }
//  }
//
//  "A Pong actor" must {
//    "send back a pong on a ping" in {
//      val pongActor = system.actorOf(PongActor.props)
//      pongActor ! PingActor.PingMessage("ping")
//      expectMsg(PongActor.PongMessage("pong"))
//    }
//  }

}
