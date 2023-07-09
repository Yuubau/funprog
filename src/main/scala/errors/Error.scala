package errors

sealed trait Error extends Throwable

case class ConfigError(confLine: String) extends Error {
  override def getMessage: String = s"Le champ de configuration $confLine est inexistant ou mal renseigné"
}
case class ParsingError(message: String) extends Error {
  override def getMessage: String = message
}
case class OpenFileError(message: String, path: String) extends Error {
  override def getMessage: String = s"File : $path\n" + message
}
case class InstructionError(message: String, orientation: String) extends Error {
  override def getMessage: String = s"Orientation : $orientation\n" + message
}
