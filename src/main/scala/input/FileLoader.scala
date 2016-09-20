package input

import models.{Location, Trace}
import org.joda.time.{DateTime, DateTimeZone}

import scala.io.Source

class FileLoader(filename: String, skipHeader: Boolean = true) extends InputLoader {

  implicit def str2date(str: String) = "yyyy-MM-ddTHH:mm:ss.SSS"

  override def load: Iterator[Trace] = {
    val stream = getClass.getResourceAsStream(filename)
    val source = Source.fromInputStream(stream).getLines().drop(if(skipHeader) 1 else 0)
    for(line <- source) yield {
      val data = line.split(',')
      Trace(
        uid = data(4),
        timestamp = new DateTime(data(0), DateTimeZone.UTC),
        Location(x = data(1).toDouble, y = data(2).toDouble, floor = data(3).toInt)
      )
    }
  }

}
