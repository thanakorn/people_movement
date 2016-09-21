package output
import java.io.{BufferedWriter, File, FileWriter}

import models.Meeting

class FileGenerator(filename: String) extends OutputGenerator{

  override def write(meetings: List[Meeting]): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write("uid1,uid2,timestamp,x,y,floor\n")
    meetings.map(m => bw.write(s"${m.uid1},${m.uid2},${m.timestamp.toString("yyyy-MM-dd'T'HH:mm:ss.SSS")},${m.location.x},${m.location.y},${m.location.floor}\n"))
    bw.close()
  }

}
