package main

import java.lang.Math._

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}
import input.FileLoader
import logic.SimpleMeetingFinder
import models._
import preprocessing.Preprocessor
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends Preprocessor {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val uid1 = "78b85537"
    val uid2 = "27cb9c6f"
    val flow = RunnableGraph.fromGraph(GraphDSL.create(){implicit builder =>
      import GraphDSL.Implicits._

      // Create source from file
      val inputLoader = new FileLoader("/dataset.csv")
      val dataset = inputLoader.load
      val source = builder.add(Source[Traces](List(dataset.toList)))

      // Pre-process data
      val filterUID = builder.add(Flow[Traces].map(traces => traces.filter(t => (t.uid == uid1|| t.uid == uid2))))
      val ignoreMillisec = builder.add(Flow[Traces].map(traces => floorTimestamp(traces, 3)))
      val groupFloor = builder.add(Flow[Traces].map(traces => groupByFloor(traces)))
      val groupId = builder.add(Flow[Map[Floor, Traces]].map(floorMap => floorMap.map { case(floor, traces) => floor -> groupById(traces).values }))
      val generateFloorPairs = builder.add(Flow[Map[Floor, Iterable[Traces]]].map(floorTraces => {
        floorTraces.foldLeft(Iterable[(Traces, Traces)]())((pairs , tracesList) => pairs ++ pairElements[Traces](tracesList._2))
      }))

      // Calculation
      def euclideanDistance(a: Location, b: Location): Distance = sqrt(pow(a.x - b.x, 2) + pow(a.y - b.y, 2))
      val maxMeetingDistance = 2.0f
      val finder = new SimpleMeetingFinder(euclideanDistance, maxMeetingDistance)
      val calc = builder.add(Flow[Iterable[(Traces, Traces)]].map(pairs => {
        val meetingFSeq = Future.sequence(pairs.map(pair => Future {
          finder.findMeeting(pair._1, pair._2)
        }))
        meetingFSeq.flatMap(x => Future(x.flatten))
      }))

      // Sink
      val result = builder.add(Sink.foreach[Future[Iterable[Meeting]]](fut => {
        fut.map(meetings => if(meetings.nonEmpty) meetings.foreach(m => println(m)) else println("No meeting occurs") )
      }))

      source ~> filterUID ~> ignoreMillisec ~> groupFloor ~> groupId ~> generateFloorPairs ~> calc ~> result
      ClosedShape
    })
    flow.run()
  }

}
