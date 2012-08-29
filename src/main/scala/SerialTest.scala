package com.moixa

import java.io.InputStream
import scala.collection.JavaConversions._
import gnu.io._
import proto.core._
import proto.datalink._
import com.moixa.asyncFrame.FramedMessage

object SerialTest {

  class SerialReader(in: InputStream) extends SerialPortEventListener {
    val reader = new InputTokenReader(in)
    var state: FrameState = LookingForFrameBoundary

    def serialEvent(event: SerialPortEvent) = {
      while (in.available > 0) {
        state = state.receiveToken(reader.read)

        state match {
          case fc: FrameComplete => {
            println("Message: " + new String(fc.frame.body.toByteArray))
            state = LookingForFrameBoundary
          }
          case e: ErrorState => {
            println(e.error)
            state = LookingForFrameBoundary
          }
          case _ => // nothing
        }
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
    val outputStream = port.getOutputStream()

    val frame = Frame(msg.toByteArray())
    val sender = FramedMessage.sender(outputStream)
    sender.writeFrame(frame)

    // Give the reader enough time to revceive the response
    Thread.sleep(2000)

    port.removeEventListener()
    println("end")
    port.close()
  }

}
