package errors

object ErrorLogger {

  def log[E <: Throwable](e: E): Unit = {
    println(e.getMessage)
  }

}
