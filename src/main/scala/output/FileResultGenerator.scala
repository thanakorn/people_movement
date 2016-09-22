package output
import java.io.{BufferedWriter, File, FileWriter}

import models.Meeting
import org.joda.time.DateTime

class FileResultGenerator(filename: String) extends ResultGenerator{

  override def write(meetings: List[Meeting]): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write("timestamp,uid1,x1,y1,uid2,x2,y2,floor\n")
    meetings.map(m => {
      bw.write(s"${new DateTime(m.timestamp).toString("yyyy-MM-dd'T'HH:mm:ss.SSS")},${m.uid1},${m.location1.x},${m.location1.y},${m.uid2},${m.location2.x},${m.location2.y},${m.location1.floor}\n")
    })
    bw.close()
  }

}
