import better.files.File
import classes.conf.AppConfiguration
import classes.writer.WriterController
import classes.domain.Lawn
import classes.parser.LawnParser
import errors._

import scala.util.{Failure, Success, Try}

object Main extends App {

  WriterController.initWriters()

  val inputFilePath: Try[String] = AppConfiguration.getInputFileName

  val Tlawn: Try[Lawn] = {
    inputFilePath match {
      case Success(filePath) => parseLawn(filePath)
      case Failure(error) => {
        ErrorLogger.log(error)
        Failure(error)
      }
    }
  }

  Tlawn match {
    case Success(lawn) => lawn.execMowers()
    case Failure(exception) => ErrorLogger.log(exception)
  }

  def parseLawn(filePath: String): Try[Lawn] = {

    val lawnParser: LawnParser = new LawnParser
    // Creating a File object
    val file = File(filePath)
    if(!file.exists) {
      Failure(OpenFileError("Imposible d'ouvrir le fichier", filePath))
    }
    else {
      val lines: Try[Option[String]] = Success(Some(file.contentAsString))
      lines match {
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
