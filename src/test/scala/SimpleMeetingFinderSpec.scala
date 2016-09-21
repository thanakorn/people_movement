import models.{Distance, Location, Meeting, Trace}
import org.scalatest._
import java.lang.Math._

import logic.SimpleMeetingFinder
import org.joda.time.DateTime

class SimpleMeetingFinderSpec extends FlatSpec with Matchers {

  def distance(a: Location, b: Location): Distance = sqrt(pow(a.x - b.x, 2) + pow(a.y - b.y, 2))

  val meetingFinder = new SimpleMeetingFinder(distance, 2.0)

  "SimpleMeetingFinder" should "return empty for traces with different timestamp" in {
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

  "SimpleMeetingFinder" should "return empty for traces with different floor" in {
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

  "SimpleMeetingFinder" should "return meeting list between t1 and t2 if exist" in {

    val uid1 = "20bcf0ad"
    val t1 = List(
      Trace(timestamp = 1, location = Location(x = 1.0, y = 1.0, floor = 2), uid = uid1),
      Trace(timestamp = 2, location = Location(x = 1.5, y = 1.0, floor = 2), uid = uid1),
      Trace(timestamp = 3, location = Location(x = 1.7, y = 1.2, floor =	2), uid = uid1),
      Trace(timestamp = 4, location = Location(x = 2.0,	y = 1.5, floor = 2), uid = uid1),
      Trace(timestamp = 5, location = Location(x = 3.0, y	= 3.0, floor =	2), uid =	uid1),
      Trace(timestamp = 6, location = Location(x = 4.0,	y = 3.0, floor = 2), uid = uid1)
    )

    val uid2 = "da0fcb02"
    val t2 = List(
      Trace(timestamp = 1, location = Location(x = 1.0, y = 5.0, floor = 2), uid = uid2),
      Trace(timestamp = 2, location = Location(x = 1.0, y = 4.0, floor = 2), uid = uid2),
      Trace(timestamp = 3, location = Location(x = 2.0, y = 4.0, floor =	2), uid = uid2),
      Trace(timestamp = 4, location = Location(x = 3.0,	y = 4.0, floor = 2), uid = uid2),
      Trace(timestamp = 5, location = Location(x = 3.2, y	= 3.0, floor =	2), uid =	uid2),
      Trace(timestamp = 6, location = Location(x = 3.5,	y = 2.0, floor = 2), uid = uid2)
    )

    val meetings = meetingFinder.findMeeting(t1, t2)
    meetings.size should be(2)
    meetings(0) should be(Meeting(5, uid1, Location(x = 3.0, y	= 3.0, floor =	2), uid2, Location(x = 3.2, y	= 3.0, floor =	2)))
    meetings(1) should be(Meeting(6, uid1, Location(x = 4.0, y	= 3.0, floor =	2), uid2, Location(x = 3.5, y	= 2.0, floor =	2)))
  }

}
