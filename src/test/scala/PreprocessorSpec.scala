import models.{Location, Trace}
import org.scalatest.{FlatSpec, Matchers}
import preprocessing.Preprocessor

class PreprocessorSpec extends FlatSpec with Matchers{

  val preprocessor = new Preprocessor{}
  val location = Location(0, 0, 0)

  "floorTimestamp" should "remove last n digits of timestamp" in {
    val traces = List(
      Trace("1", 1000, location),
      Trace("2", 1050, location),
      Trace("3", 2100, location),
      Trace("2", 2000, location)
    )
    val preprocessedTraces = preprocessor.floorTimestamp(traces, 2)
    preprocessedTraces(0).timestamp should be(1000)
    preprocessedTraces(1).timestamp should be(1000)
    preprocessedTraces(2).timestamp should be(2100)
    preprocessedTraces(3).timestamp should be(2000)
  }

  "groupByFloor" should "group trace in same floor" in {
    val traces = List(
      Trace("1", 1000, location),
      Trace("2", 1050, location.copy(floor = 2)),
      Trace("3", 2100, location.copy(floor = 1)),
      Trace("2", 2000, location.copy(floor = 2))
    )
    val floorTraces = preprocessor.groupByFloor(traces)
    floorTraces.getOrElse(0, Nil) should be(List(Trace("1", 1000, location)))
    floorTraces.getOrElse(1, Nil) should be(List(Trace("3", 2100, location.copy(floor = 1))))
    floorTraces.getOrElse(2, Nil) should be(List(Trace("2", 1050, location.copy(floor = 2)), Trace("2", 2000, location.copy(floor = 2))))
  }

}
