import better.files.File
import classes.LawnParser
import classes.Lawn
import errors._

import scala.util.{Failure, Success, Try}

object Main extends App {

  parseLawn()
  def parseLawn(): Unit = {
    val lawnParser: LawnParser = new LawnParser
    val path = "../ressources/mower.txt"

    val lines: Try[Option[String]] = getContentFile(path)
    val lawn: Try[Lawn] = lines match {
      case Success(content) =>
        content match {
          case Some(res) =>
            lawnParser.parseLawn(res) match {
              case Some(lawn) => Success(lawn)
              case None =>
                Failure(ParsingError("erreur de synthaxe dans le fichier"))
            }
          case None => Failure(OpenFileError("fichier vide", path))
        }
      case Failure(_) => Failure(OpenFileError("fichier introuvable", path))
    }
    println(lawn)
  }

  def getContentFile(path: String): Try[Option[String]] = {
    val file = Try(File(path))
    file match {
      case Success(content) => Success(Some(content.lines.toString()))
      case Failure(_) =>
        Failure(OpenFileError("erreur d'ouverture du fichier", path))
    }
  }
}
