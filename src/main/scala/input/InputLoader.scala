package input

import models.Traces

trait InputLoader {

  def load : Traces

}
