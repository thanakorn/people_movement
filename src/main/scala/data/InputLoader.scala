package data

import models.Trace

trait InputLoader {

  def load : Iterator[Trace]

}
