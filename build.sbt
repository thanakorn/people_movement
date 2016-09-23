name := """people_movement"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest"  % "2.2.4" % "test",
  "joda-time"     %   "joda-time" % "2.9.4",
  "com.typesafe.akka" % "akka-stream_2.11" % "2.4.10"
)

