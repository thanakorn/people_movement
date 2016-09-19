package models

import org.joda.time.DateTime

case class Trace(id: String, timestamp: DateTime, location: Location)
