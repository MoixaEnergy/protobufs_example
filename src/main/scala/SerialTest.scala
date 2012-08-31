package com.moixa

import java.io.InputStream
import scala.collection.JavaConversions._
import gnu.io._

import proto.core._
import proto.datalink._
import proto.network._

object SerialTest {

  object MessageLogger extends PacketReceiver {
    def protocols = List(0xff)

    def receivePacket(p: Packet) {
      println("Message: " + new String(p.body.toByteArray))
    }
  }

  class SerialReader(in: InputStream) extends SerialPortEventListener {
    val switch = new PacketSwitch(List(MessageLogger))
    val fsDecoder = new FrameStateDecoder(switch)
    val tokenDriver = new InputTokenDriver(fsDecoder)

    val reader = new InputTokenReader(in)
    var state: FrameState = LookingForFrameBoundary

    def serialEvent(event: SerialPortEvent) = {
      while (in.available > 0) {
        tokenDriver.processByte(in.read)
      }
    }
  }

  def main(args: Array[String]) = {
    val deviceFile = args(0)
    val portList = CommPortIdentifier.getPortIdentifiers
    portList.toList.foreach { p =>
      val port = p.asInstanceOf[CommPortIdentifier]
      println("port: " + port.getName() + ":" + port.getPortType())
    }

    val portIdentifier = CommPortIdentifier.getPortIdentifier(deviceFile)

    val port = {
      val p = portIdentifier.open("moixa_uart", 2000)
      p.asInstanceOf[SerialPort]
    }
    port.setSerialPortParams(
      19200, SerialPort.DATABITS_8,
      SerialPort.STOPBITS_1,
      SerialPort.PARITY_NONE)

    port.addEventListener(new SerialReader(port.getInputStream()))
    port.notifyOnDataAvailable(true);

    val msg = FileTest.mcsMsg
    val packet = new Packet(0x01, QuadAddress.ZERO, QuadAddress.ZERO, OctetData(msg.toByteArray().map(Octet(_)).toList))
    val frame = Frame(packet)

    val outputStream = port.getOutputStream()
    val fos = new FrameOutputStream(outputStream)
    fos.writeFrame(frame)

    // Give the reader enough time to receive the response
    Thread.sleep(2000)

    port.removeEventListener()
    println("end")
    port.close()
  }

}
