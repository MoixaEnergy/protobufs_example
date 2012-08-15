package com.moixa

import com.google.protobuf

import scalax.io._
import com.moixa.protocol._

object FileTest { 
  val bmsMsg = 
    ZigbeeMessage( 
      ZigbeeAddress(DeviceType.BMS, 111111),
      ZigbeeAddress(DeviceType.MOIXA_HUB, 222222),
      Some(BmsMessage( BmsMessageType.STATUS, Some(BmsStatus(Some(99))))),
      None
    )

  val mcsMsg = 
    ZigbeeMessage( 
      ZigbeeAddress(DeviceType.MCS, 111111),
      ZigbeeAddress(DeviceType.MOIXA_HUB, 222222),
      None,
      Some(McsMessage( 
        McsMessageType.MCS_STATUS, 
        Some(
          McsStatus(
            Some(30), None, Some(77),
            Vector(McsClamp(1, 33.333f),McsClamp(2, 66.898f)))))))
        


  def test(fileName: String, msg: ZigbeeMessage) = { 
    println(msg)
    val fio = new java.io.FileOutputStream(fileName)
    fio.write(msg.toByteArray)
    fio.close()
    val input:Input = Resource.fromFile(fileName)
    val nmsg = ZigbeeMessage.defaultInstance.mergeFrom(input.byteArray)
    println(nmsg)
    assert(msg == nmsg, "input and output don't match")
  }

  def main(args: Array[String]): Unit = {
    test("moixa.bms.scala.out", bmsMsg)
    test("moixa.mcs.scala.out", mcsMsg)
  }  
}
