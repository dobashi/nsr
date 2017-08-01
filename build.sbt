name := "nsr"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= {
  Seq(
    "com.typesafe" % "config" % "1.+",
//    "org.json4s" %% "json4s-native" % "3.+",
    "org.log4s" %% "log4s" % "1.3.+",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.+",
    "ch.qos.logback" % "logback-classic" % "1.2.+",
    "org.scalatest" %% "scalatest" % "3.+" % Test
  )
}