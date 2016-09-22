package output

import models.Meeting

trait ResultGenerator {

  def write(meetings: List[Meeting]): Unit

}
