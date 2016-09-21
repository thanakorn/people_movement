package output

import models.Meeting

trait OutputGenerator {

  def write(meetings: List[Meeting]): Unit

}
