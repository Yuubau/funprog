package errors

sealed trait Error extends Throwable

case class ParsingError(message: String) extends Error
case class OpenFileError(message:String,path :String) extends Error
case class InstructionError(message: String, orientation: String) extends Error