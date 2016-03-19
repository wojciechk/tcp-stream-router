package com.enavrt

import akka.actor.{ActorLogging, Actor, Props}
import akka.event.LoggingReceive
import dk.dma.ais.message.AisMessage
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
        val message: AisMessage = p.tryGetAisMessage
        log.info(s"${message}")
      }
      else {
        log.warning(s"could not decode $m")
      }
    }
  }
}

