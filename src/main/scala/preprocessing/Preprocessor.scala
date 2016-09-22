package preprocessing

import models.{Floor, Trace, Traces, UID}
import java.lang.Math._

trait Preprocessor {

  def floorTimestamp(traces: Traces, digits: Int = 3): Traces = traces.foldRight(List[Trace]())((t, traces) => floorTimestamp(t, digits) :: traces)

  def groupByFloor(traces: Traces): Map[Floor, Traces] = traces.groupBy(_.location.floor)

  def groupById(traces: Traces): Map[UID, Traces] = traces.groupBy(_.uid)

  def pairElements[T](elements: List[T]): List[(T, T)] = {
    val elementsWithIndex = elements.zipWithIndex
    for{
      (t1, index1) <- elementsWithIndex
      (t2, index2) <- elementsWithIndex
      if(index1 < index2)
    } yield (t1, t2)
  }

  private def floorTimestamp(t: Trace, digits: Int) = t.copy(timestamp = (t.timestamp / pow(10, digits).toLong) * pow(10, digits).toLong)

}
