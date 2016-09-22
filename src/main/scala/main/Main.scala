package main

import java.lang.Math._
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}
import input.FileLoader
import models._
import preprocessing.Preprocessor
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends Preprocessor{

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val flow = RunnableGraph.fromGraph(GraphDSL.create(){implicit builder =>
      import GraphDSL.Implicits._

      // Create source from dataset
      val inputLoader = new FileLoader("/super_small_dataset.csv")
      val dataset = inputLoader.load
      val sources = builder.add(Source[Traces](List(dataset)))

      // Pre-process data
      val ignoreMillisec = Flow[Traces].map(traces => floorTimestamp(traces, 3))

      // Sink
      val result = builder.add(Sink.foreach[Traces](_.map(t => println(t))))

      sources ~> ignoreMillisec ~> result
      ClosedShape
    })
    flow.run()
  }

}
