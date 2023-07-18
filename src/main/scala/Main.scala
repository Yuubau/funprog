import better.files.File
import classes.domain.Lawn
import classes.logger.Logger
import classes.parser.LawnParser
import conf.AppConfiguration
import classes.writer.WriterController
import errors._

import scala.util.{Failure, Success, Try}

object Main extends App {

  WriterController.initWriters()

  val inputFilePath: Try[String] = AppConfiguration.getInputFileName

  val Tlawn: Try[Lawn] = inputFilePath.flatMap(parseLawn)

  Tlawn match {
    case Success(lawn) =>
      val res = lawn.execMowers()
      res match {
        case Left(errors) =>
          for (instructionError <- errors) {
            Logger.logError(instructionError)
          }
        case Right(_) => Logger.log("Aucune erreur lors de l'execution des instructions")
      }
    case Failure(exception) => Logger.logError(exception)
  }

  def parseLawn(filePath: String): Try[Lawn] = {
    val lawnParser = new LawnParser
    val file = File(filePath)
    if (!file.exists) {
      Failure(OpenFileError("Impossible d'ouvrir le fichier", filePath))
    } else {
      val content: Try[String] = Success(file.contentAsString)
      content.flatMap { res =>
        lawnParser.parseLawn(res) match {
          case Some(lawn) => Success(lawn)
          case None => Failure(ParsingError("Erreur de syntaxe dans le fichier"))
        }
      }
    }
  }
}