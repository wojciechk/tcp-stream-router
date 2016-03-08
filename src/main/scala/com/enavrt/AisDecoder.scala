package com.enavrt

import akka.actor.{ActorLogging, Actor, Props}
import akka.event.LoggingReceive
import dk.dma.ais.packet.AisPacket

/**
  * Created by wkaminski on 25.02.16.
  */
object AisDecoder {
  def props =
    Props(classOf[AisDecoder])
}

class AisDecoder extends Actor with ActorLogging{
  override def receive: Receive = LoggingReceive{
    case m: String => {

      val p=AisPacket.readFromString(m)
      if(p!=null) {
        log.info(s"${p.tryGetAisMessage()}")
      }
      else {
        log.warning(s"could not decode $m")
      }
    }
  }
}

