package classes.logger

object Logger {

  def logError[E <: Throwable](e: E): Unit = {
    println(e.getMessage)
  }

  def log(str: String): Unit = {
    println(str)
  }

}
