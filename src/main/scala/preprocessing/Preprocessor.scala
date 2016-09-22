package preprocessing

import models.{Floor, Trace, Traces, UID}
import java.lang.Math._

trait Preprocessor {

  def floorTimestamp(traces: Traces, digits: Int = 3): Traces = traces.foldRight(List[Trace]())((t, traces) => floorTimestamp(t, digits) :: traces)

  def groupByFloor(traces: Traces): Map[Floor, Traces] = traces.groupBy(_.location.floor)

  def groupById(traces: Traces): Map[UID, Traces] = traces.groupBy(_.uid)

  def sortByTimestamp(traces: Traces): Traces = traces.sortBy(_.timestamp)

  def pairTraces(traces: List[Traces]): List[(Traces, Traces)] = {
    val tracesWithIndex = traces.zipWithIndex
    for{
      (t1, index1) <- tracesWithIndex
      (t2, index2) <- tracesWithIndex
      if(index1 < index2)
    } yield (t1, t2)
  }

  private def floorTimestamp(t: Trace, digits: Int) = t.copy(timestamp = (t.timestamp / pow(10, digits).toLong) * pow(10, digits).toLong)

}
