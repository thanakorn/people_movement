package logic
import models._

class SimpleMeetingFinder(override val distance: (Location, Location) => Distance,
                          override val maxMeetingDistance: Distance) extends MeetingFinder{

  private def isMet(t1: Trace, t2: Trace): Boolean = (t1.timestamp == t2.timestamp) && (t1.location.floor == t2.location.floor) && (distance(t1.location, t2.location) <= maxMeetingDistance)

  override def findMeeting(t1: Traces, t2: Traces): List[Meeting] = {
    isTimeOverlap(t1, t1) match {
      case true =>
        t1.map(t => {
          val meet = t2.find(s => isMet(t, s))
          if(meet.isDefined) Option(Meeting(t.timestamp, t.uid, t.location, meet.get.uid, meet.get.location ))
          else None
        }).flatten

      case false => Nil
    }
  }

  private def isTimeOverlap(t1: Traces, t2: Traces): Boolean = {
    (t1.last.timestamp >= t2.head.timestamp) && (t1.last.timestamp <= t2.last.timestamp) ||
      (t2.last.timestamp >= t1.head.timestamp) && (t2.last.timestamp <= t1.last.timestamp)
  }

}
