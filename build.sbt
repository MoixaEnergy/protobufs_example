//import AssemblyKeys._ 

name := "ProtobufsTest"

organization := "com.moixa"

// version := "0.2.2-SNAPSHOT"

version := "0.0.1"

scalaVersion := "2.9.2"


resolvers += "scalatools-releases" at "http://scala-tools.org/repo-releases/"

resolvers += "scalatools-snapshots" at "http://scala-tools.org/repo-snapshots/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases" 

libraryDependencies ++= Seq(
  // "net.sandrogrzicic" % "ScalaBuff" % "0.9-SNAPSHOT",
  "com.google.protobuf" % "protobuf-java" % "2.4.1",
  "com.weiglewilczek.slf4s" % "slf4s_2.9.1" % "1.0.7",
  "org.specs2" %% "specs2" % "1.10" % "test",
  "org.scala-tools.time" % "time_2.9.1" % "0.5",
  "org.scalaz" %% "scalaz-core" % "6.0.4",
  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.0",
  "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.0"
)

resolvers += "Java.net Maven 2 Repo" at "http://download.java.net/maven/2"

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies += "org.clapper" % "avsl_2.9.1" % "0.3.8"

scalacOptions += "-deprecation"

// TaskKey[Unit]("gen_proto") := net.sandrogrzicic.scalabuff.compiler.ScalaBuff("bms.proto")


