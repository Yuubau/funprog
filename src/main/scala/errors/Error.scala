package errors

sealed abstract class Error extends Throwable

case class ConfigError(confLine: String) extends Error {
  override def getMessage: String = s"Le champ de configuration ${confLine} est inexistant ou mal renseigné"
}
case class ParsingError(message: String) extends Error {
  override def getMessage: String = message
}
case class OpenFileError(message: String, path: String) extends Error {
  override def getMessage: String = message + path
}
case class InstructionError(message: String, orientation: String) extends Error {
  override def getMessage: String = message + orientation
}
