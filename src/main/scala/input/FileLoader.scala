package input

import models.{Location, Trace, Traces}
import org.joda.time.{DateTime, DateTimeZone}

import scala.io.Source

class FileLoader(filename: String, skipHeader: Boolean = true) extends InputLoader {

  implicit def str2date(str: String) = "yyyy-MM-ddTHH:mm:ss.SSS"

  override def load = {
    val stream = getClass.getResourceAsStream(filename)
    val source = Source.fromInputStream(stream).getLines().drop(if(skipHeader) 1 else 0)
    val dataset = for(line <- source) yield {
      val data = line.split(',')
      Trace(data(4), (new DateTime(data(0), DateTimeZone.UTC)).getMillis, Location(data(1).toDouble, data(2).toDouble, data(3).toInt))
    }
    dataset
  }

}
