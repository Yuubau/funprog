import better.files.File
import classes.{Lawn, LawnParser}
import errors._

import scala.util.{Failure, Success, Try}

object Main extends App {

  parseLawn()
  def parseLawn(): Unit = {
    val lawnParser: LawnParser = new LawnParser
    // Creating a File object
    val file = File("mower.txt")
    if(!file.exists) {
      println("Imposible d'ouvrir le fichier")
    } else {
      val lines: Try[Option[String]] = Success(Some(file.contentAsString))
      val Tlawn: Try[Lawn] = lines match {
        case Success(content) =>
          content match {
            case Some(res) =>
              lawnParser.parseLawn(res) match {
                case Some(lawn) => Success(lawn)
                case None =>
                  Failure(ParsingError("erreur de synthaxe dans le fichier"))
              }
            case None => Failure(OpenFileError("fichier vide", file.path.toString))
          }
        case Failure(e) => Failure(e)
      }
      println(Tlawn)
      Tlawn match {
        case Success(lawn) => lawn.execMowers() match {
          case Right(value) => println(value)
          case Left(value)=>println(value)
        }
        case Failure(exception) => println(exception)
      }
    }

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
