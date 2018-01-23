name := "akkademy-db"

organization := "com.akkademy-db"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.2"

lazy val akkaVersion = "2.5.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "junit" % "junit" % "4.12")

libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0"

libraryDependencies += "com.typesafe.akka" %% "akka-remote" % akkaVersion

mappings in (Compile, packageBin) ~= {
  _.filterNot {
    case (_, name) => Seq("server-application.conf").contains(name)
  }
}