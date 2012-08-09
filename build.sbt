//import AssemblyKeys._ 

import com.typesafe.startscript.StartScriptPlugin

name := "ProtobufsTest"

organization := "com.moixa"

// version := "0.2.2-SNAPSHOT"

version := "0.0.1"

scalaVersion := "2.9.2"


resolvers += "scalatools-releases" at "http://scala-tools.org/repo-releases/"

resolvers += "scalatools-snapshots" at "http://scala-tools.org/repo-snapshots/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases" 

// resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
//                    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies ++= Seq(
  // "net.sandrogrzicic" % "ScalaBuff" % "0.9-SNAPSHOT",
  "com.google.protobuf" % "protobuf-java" % "2.4.1",
  "com.weiglewilczek.slf4s" % "slf4s_2.9.1" % "1.0.7",
  "org.specs2" %% "specs2" % "1.10" % "test",
  "org.scala-tools.time" % "time_2.9.1" % "0.5",
  "org.scalaz" %% "scalaz-core" % "6.0.4",
  "net.databinder" %% "dispatch-http-json" % "0.8.8",
  "net.databinder" %% "dispatch-http" % "0.8.8",
  "net.databinder" %% "dispatch-nio" % "0.8.8",
  "net.databinder" %% "unfiltered-netty-websockets" % "0.6.3",
  "net.databinder" %% "unfiltered-spec" % "0.6.3",
  "net.databinder" %% "unfiltered-json" % "0.6.3",
  "net.debasishg" % "sjson_2.9.1" % "0.17",
  "org.scala-tools" % "scala-stm_2.9.1" % "0.5",
  "org.streum" % "configrity_2.9.1" % "0.9.0",
  "org.scalaquery" % "scalaquery_2.9.0-1" % "0.9.5",
  "com.h2database" % "h2" % "1.3.162",
  "net.debasishg" % "redisclient_2.9.1" % "2.5",
  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.0",
  "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.0",
  "com.chuusai" %% "shapeless" % "1.2.2",
  "com.recursivity" % "recursivity-commons_2.9.1" % "0.6",
  "com.typesafe.akka" % "akka-actor" % "2.0.1",
  "org.apache.commons" % "commons-email" % "1.2",
  "commons-httpclient" % "commons-httpclient" % "3.1",
  "com.codecommit" % "anti-xml_2.9.1" % "0.3"
)

//  "org.xerial" % "sqlite-jdbc" % "3.6.20",

  // "com.mongodb.casbah" % "casbah_2.9.1" % "2.1.5-1",	

//  "net.debasishg" % "redisclient_2.9.1" % "2.4.2",

//  "com.github.wrwills" % "formaggio_2.9.1" % "0.2.2",

//  "org.scala-tools.testing" %% "specs" % "1.6.9" % "test",
//  

resolvers += "Java.net Maven 2 Repo" at "http://download.java.net/maven/2"

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies += "org.clapper" % "avsl_2.9.1" % "0.3.8"

scalacOptions += "-deprecation"

publishTo := Some(Resolver.file("testmoixa", new java.io.File("var/www/repo")))

publishArtifact in packageSrc := false

seq(StartScriptPlugin.startScriptForClassesSettings: _*)