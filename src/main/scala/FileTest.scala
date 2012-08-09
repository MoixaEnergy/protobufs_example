package com.moixa

import com.google.protobuf

import scalax.io._
import com.moixa.bms._

object FileTest { 
  def test = { 
    val fileName = "bms.scala.out"
    val msg = BmsMessage( MessageType.STATUS, Some(BmsStatus(Some(99))))
    //val msg = BmsStatus(Some(99))
    println(msg)
    val fio = new java.io.FileOutputStream(fileName)
    fio.write(msg.toByteArray)
    fio.close()
    val input:Input = Resource.fromFile(fileName)
    val nmsg = BmsMessage.defaultInstance.mergeFrom(input.byteArray)
    println(nmsg)
    assert(msg == nmsg, "input and output don't match")
  }

  def main2(args: Array[String]): Unit = {
    test
  }  
}
