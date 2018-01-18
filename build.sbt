name := "akkademy-db"

version := "1.0"

scalaVersion := "2.12.2"

lazy val akkaVersion = "2.5.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "junit" % "junit" % "4.12")

libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0"
