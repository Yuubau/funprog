package classes.writer

import classes.domain.LawnHistory
import classes.logger.Logger
import conf.AppConfiguration

import scala.util.{Failure, Success}

object WriterController {

  private val jsonWriter: Option[JsonWriter] = {
    AppConfiguration.hasJsonOutput match {
      case Success(useJson) => {
        if (useJson) {
          AppConfiguration.getJsonOutputPath match {
            case Success(value) => Some(JsonWriter(value))
            case Failure(exception) => {
              Logger.logError(exception)
              None
            }
          }
        }
        else None
      }
      case Failure(exception) => {
        Logger.logError(exception)
        None
      }
    }
  }

  private val csvWriter: Option[CsvWriter] = {
    AppConfiguration.hasCsvOutput match {
      case Success(useCsv) => {
        if (useCsv) {
          AppConfiguration.getCsvOutputPath match {
            case Success(value) => Some(CsvWriter(value))
            case Failure(exception) => {
              Logger.logError(exception)
              None
            }
          }
        }
        else None
      }
      case Failure(exception) => {
        Logger.logError(exception)
        None
      }
    }
  }

  private val yamlWriter: Option[YamlWriter] = {
    AppConfiguration.hasYamlOutput match {
      case Success(useYaml) => {
        if (useYaml) {
          AppConfiguration.getYamlOutputPath match {
            case Success(value) => Some(YamlWriter(value))
            case Failure(exception) => {
              Logger.logError(exception)
              None
            }
          }
        }
        else None
      }
      case Failure(exception) => {
        Logger.logError(exception)
        None
      }
    }
  }

  private def initWriter[W <: FileWriter](writer: Option[W]): Unit = {
    writer match {
      case Some(value) => value.initFile()
      case None => ()
    }
  }

  def initWriters(): Unit = {
    initWriter(jsonWriter)
    initWriter(csvWriter)
    initWriter(yamlWriter)
  }

  private def writeHistory[W <: FileWriter](writer: Option[W], lawnHistory: LawnHistory): Unit = {
    writer match {
      case Some(value) => value.writeLawnHistory(lawnHistory)
      case None => ()
    }
  }

  def writeHistory(lawnHistory: LawnHistory): Unit = {
    writeHistory(jsonWriter, lawnHistory)
    writeHistory(csvWriter, lawnHistory)
    writeHistory(yamlWriter, lawnHistory)
  }

  private def writeFooter[W <: FileWriter](writer: Option[W]): Unit = {
    writer match {
      case Some(value) => value.writeFooter()
      case None => ()
    }
  }

  def writeFooter(): Unit = {
    writeFooter(jsonWriter)
    writeFooter(csvWriter)
    writeFooter(yamlWriter)
  }

}
