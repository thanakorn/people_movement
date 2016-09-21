package logic

import models._

trait MeetingFinder {

  val distance: (Location, Location) => Distance

  val maxMeetingDistance: Distance

  def findMeeting(t1: Traces, t2: Traces): List[Meeting] = ???

}
