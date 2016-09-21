import models.{Distance, Location, Trace}
import org.scalatest._
import java.lang.Math._

import logic.SimpleMeetingFinder
import org.joda.time.DateTime

class SimpleMeetingFinderSpec extends FlatSpec with Matchers {

  def distance(a: Location, b: Location): Distance = sqrt(pow(a.x - b.x, 2) + pow(a.y - b.y, 2))

  val meetingFinder = new SimpleMeetingFinder(distance, 2.0)

  "BruteForceMeetingDetection" should "return empty for traces with different timestamp" in {
    val uid1 = "uid1"
    val t1 = List(
      Trace(uid1, 0, Location(1.0, 1.0, 1)),
      Trace(uid1, 1, Location(1.0, 1.0, 1))
    )

    val uid2 = "uid2"
    val t2 = List(
      Trace(uid2, 10000, Location(1.0, 1.0, 1)),
      Trace(uid2, 11000, Location(1.0, 1.0, 1))
    )

    meetingFinder.findMeeting(t1, t2).size should be(0)
  }

  "BruteForceMeetingDetection" should "ignore millisecond in timestamp" in {
    val uid1 = "uid1"
    val t1 = List(
      Trace(uid1, 0, Location(1.0, 1.0, 1)),
      Trace(uid1, 1, Location(1.0, 1.0, 1))
    )

    val uid2 = "uid2"
    val t2 = List(
      Trace(uid2, 100, Location(1.0, 1.0, 1)),
      Trace(uid2, 110, Location(11.30, 1.0, 1))
    )

    meetingFinder.findMeeting(t1, t2).size should be(2)
  }

  "BruteForceMeetingDetection" should "return empty for traces with different floor" in {
    val uid1 = "uid1"
    val t1 = List(
      Trace(uid1, 0, Location(1.0, 1.0, 1)),
      Trace(uid1, 1, Location(1.0, 1.0, 1))
    )

    val uid2 = "uid2"
    val t2 = List(
      Trace(uid2, 0, Location(1.0, 1.0, 2)),
      Trace(uid2, 1, Location(1.0, 1.0, 3))
    )

    meetingFinder.findMeeting(t1, t2).size should be(0)
  }
//
//  "BruteForceMeetingDetection" should "return empty for traces with different floor" in {
//
//    val uid1 = "20bcf0ad"
//    val t1 = List(
//      Trace(timestamp = new DateTime("2014-07-19T23:00:11.012"), location = Location(x = 103.77579, y = 71.40748404, floor = 2), uid = "20bcf0ad"),
//      Trace(timestamp = new DateTime("2014-07-19T23:00:12.871"), location = Location(x = 103.56561, y = 71.97631916, floor = 2), uid = "20bcf0ad"),
//      Trace(timestamp = new DateTime("2014-07-19T23:00:15.694"), location = Location(x = 103.614265, y = 71.93304878, floor =	2), uid = "20bcf0ad"),
//      Trace(timestamp = new DateTime("2014-07-19T23:00:17.135"), location = Location(x = 103.88557,	y = 71.55554158, floor = 2), uid = "20bcf0ad"),
//      Trace(timestamp = new DateTime("2014-07-19T23:00:22.163"), location = Location(x = 103.88311, y	= 71.54979263, floor =	2), uid =	"20bcf0ad"),
//      Trace(timestamp = new DateTime("2014-07-19T23:00:23.673"), location = Location(x = 103.84489,	y = 71.47096978, floor = 2), uid = "20bcf0ad"),
//      2014-07-19T23:00:27.065	103.85174	71.47569486	2	20bcf0ad,
//      2014-07-19T23:00:49.285	105.32528	71.71383265	2	20bcf0ad,
//      2014-07-19T23:00:53.472	104.6214	72.11556512	2	20bcf0ad,
//      2014-07-19T23:00:59.818	101.35653	74.030811	2	20bcf0ad,
//      2014-07-19T23:01:44.277	101.61179	73.97831723	1	20bcf0ad,
//      2014-07-19T23:01:46.300	103.398674	71.84742434	1	20bcf0ad,
//      2014-07-19T23:01:54.994	103.40437	71.8165699	1	20bcf0ad,
//    )
//
//    val uid2 = "uid2"
//    val t2 = List(
//      Trace(uid2, new DateTime(0), Location(1.0, 1.0, 2)),
//      Trace(uid2, new DateTime(1), Location(1.0, 1.0, 3)),
//      Trace(uid2, new DateTime(2), Location(1.0, 1.0, 4)),
//      Trace(uid2, new DateTime(3), Location(1.0, 1.0, 4)),
//      Trace(uid2, new DateTime(4), Location(1.0, 1.0, 5))
//    )
//
//    meetingFinder.findMeeting(t1, t2).size should be(0)
//  }

}
