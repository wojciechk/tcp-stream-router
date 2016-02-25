package com.enavrt

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.io.Tcp.Connected
import akka.testkit._
import akka.util.ByteString
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
      val probe = TestProbe()
      val listener =system.actorOf(Props(classOf[TcpListener],probe.ref))
      listener!ByteString("!AIVDM,1,1,")
      listener!ByteString(",B,13prWJW")
      listener!ByteString("P00QF4IJO?")
      listener!ByteString("`pM6gwl2<1")
      listener!ByteString("F,0*01\n")
      probe.expectMsg("!AIVDM,1,1,,B,13prWJWP00QF4IJO?`pM6gwl2<1F,0*01")
    }
  }
}
