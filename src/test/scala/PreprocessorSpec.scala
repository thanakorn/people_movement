import models.{Location, Trace, Traces}
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

  "groupByUID" should "group trace in same uid" in {
    val traces = List(
      Trace("1", 1000, location),
      Trace("2", 1000, location),
      Trace("1", 1100, location),
      Trace("2", 1200, location),
      Trace("3", 1000, location)
    )
    val floorTraces = preprocessor.groupById(traces)
    floorTraces.getOrElse("1", Nil) should be(List(Trace("1", 1000, location), Trace("1", 1100, location)))
    floorTraces.getOrElse("2", Nil) should be(List(Trace("2", 1000, location), Trace("2", 1200, location)))
    floorTraces.getOrElse("3", Nil) should be(List(Trace("3", 1000, location)))
  }

  "pairElements" should "generate all possible pair of element in list" in {
    val t1 = List(Trace("1", 1, location), Trace("1", 2, location))
    val t2 = List(Trace("2", 1, location))
    val t3 = List(Trace("3", 4, location), Trace("3", 5, location))
    val t4 = List(Trace("4", 4, location), Trace("4", 5, location))
    val traces = List(t1, t2, t3, t4)
    val pairs = preprocessor.pairElements[Traces](traces)
    pairs.length should be(6)
    pairs.contains((t1, t2)) should be(true)
    pairs.contains((t1, t3)) should be(true)
    pairs.contains((t1, t4)) should be(true)
    pairs.contains((t2, t3)) should be(true)
    pairs.contains((t2, t4)) should be(true)
    pairs.contains((t3, t4)) should be(true)
  }

}
