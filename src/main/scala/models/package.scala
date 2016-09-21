import org.joda.time.DateTime

package object models {

  type UID = String
  type Timestamp = Long
  type Position = Double
  type Distance = Double
  type Floor = Int
  type Traces = List[Trace]

}
