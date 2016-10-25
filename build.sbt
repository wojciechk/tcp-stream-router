name := """tcp-stream-router"""


version := "1.0"

scalaVersion := "2.11.7"

mainClass := Some("eu.datacompass.tcpstream.Main")

lazy val akkaVersion = "2.4.0"

resolvers += Resolver.url("dma-snapshot", url("https://repository-dma.forge.cloudbees.com/snapshot/"))
resolvers += Resolver.url("dma-release", url("https://repository-dma.forge.cloudbees.com/release/"))
resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.7",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")

fork in run := true
