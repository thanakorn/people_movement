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
      ClosedShape
    })
    flow.run()
  }

}
