name := """ais-server"""

version := "1.0"

scalaVersion := "2.11.7"

lazy val akkaVersion = "2.4.0"

resolvers += Resolver.url("dma-snapshot", url("https://repository-dma.forge.cloudbees.com/snapshot/"))
resolvers += Resolver.url("dma-release", url("https://repository-dma.forge.cloudbees.com/release/"))
resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-camel" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "com.typesafe.akka" % "akka-stream-experimental_2.11" % "2.0.3",
  "org.apache.camel" % "camel-mina2" % "2.16.2",
  "org.apache.camel" % "camel-netty4" % "2.16.2",
  "dk.dma.ais.lib" % "ais-lib-messages" % "2.3-SNAPSHOT",
  "ch.qos.logback" % "logback-classic" % "1.0.7",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")

fork in run := true
