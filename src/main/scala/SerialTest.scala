package com.moixa

import java.io.{ InputStream, OutputStream, FileDescriptor, File }
import scala.collection.JavaConversions._
import gnu.io._
import java.io.ByteArrayOutputStream
import com.moixa.asyncFrame.AsyncFrameOutputStream
import com.moixa.asyncFrame.FramedMessage

object SerialTest {

  class SerialReader(in: InputStream) extends SerialPortEventListener {
    def receiver = FramedMessage.receiver(in);

    def serialEvent(event: SerialPortEvent) = {
      receiver.receiveMessage match {
        case Left(error) => println("Error: " + error)
        case Right(bytes) => println("Message: " + new String(bytes.map(_.toByte).toArray))
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

    val bos = new ByteArrayOutputStream
    val sender = FramedMessage.sender(bos)
    sender.writeMessage(msg.toByteArray().map(_.toInt))
    val bytes = bos.toByteArray()

    println("writing message of " + bytes.length + " bytes")
    outputStream.write(bytes)

    // Give the reader enough time to revceive the response
    Thread.sleep(2000)

    port.removeEventListener()
    println("end")
    port.close()
  }

}
