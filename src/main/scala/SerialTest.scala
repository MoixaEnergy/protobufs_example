package com.moixa

import java.io.{ InputStream, OutputStream, FileDescriptor, File }
import scala.collection.JavaConversions._

import gnu.io._

object SerialTest { 

  class SerialReader(in: InputStream) extends Runnable {
    def run = {
      println("running reader")
      while (true) {
        if (in.available > 0) {
          println("reading : " + in.read() )
          //in.
        }
      }
    }
  }

  val deviceFile = "/dev/ttyAMA0"

  //val inputStream: InputStream
  //val outputStream: OutputStream

  def main(args: Array[String]) = { 
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
    //port.set
    val inputStream = port.getInputStream();
    val outputStream = port.getOutputStream()

    val reader = new Thread(new SerialReader(inputStream))
    reader.start
    outputStream.write( "test".toCharArray.map( _.byteValue ) )
    Thread.sleep(10000)
  }

  

}
