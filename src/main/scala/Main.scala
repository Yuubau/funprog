import better.files.File
import classes.LawnParser
import errors.OpenFileError

import scala.util.{Failure, Success, Try}

object Main extends App {
  def parseLawn(): Unit = {
    val LawnParser: LawnParser = new LawnParser
    val path = "../ressources/mower.txt"

    val lines: Try[Option[String]] = getContentFile(path)
    lines match {
      case Success(content) => content match {
        case Some(lawn) => ??? // execInstructions(lawn)
        case None => println(OpenFileError("fichier vide", path))
      }
      case Failure(e) => println(e)
    }
  }

  def getContentFile(path: String): Try[Option[String]] = {
    val file = Try(File(path))
    file match {
      case Success(content) => Success(Some(content.lines.toString()))
      case Failure(e) => Failure(OpenFileError("erreur d'ouverture du fichier", path))
    }
}
}