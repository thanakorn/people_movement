package preprocessing

import models.{Traces, UID}

trait Preprocessor {

  def groupById(traces: Traces): Map[UID, Traces] = traces.groupBy(_.uid)

  def sortByTimestamp(traces: Traces): Traces = traces.sortBy(_.timestamp)

}
