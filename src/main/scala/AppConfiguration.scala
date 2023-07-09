import com.typesafe.config.{Config, ConfigFactory}
import errors.ConfigError

import scala.util.{Failure, Success, Try}

object AppConfiguration {
  private val conf: Config = ConfigFactory.load()

  private def checkValue[A](value: Try[A], confLine: String): Try[A] = {
    value match {
      case Success(value) => Success(value)
      case Failure(_) => Failure(ConfigError(confLine))
    }
  }

  def getInputFileName: Try[String] = {
    val confLine = "application.input.file"
    val inputFile = Try(conf.getString(confLine))
    checkValue(inputFile, confLine)
  }

  def hasJsonOutput: Try[Boolean] = {
    val confLine = "application.output.use-json"
    val use = Try(conf.getBoolean(confLine))
    checkValue(use, confLine)
  }

  def getJsonOutputPath: Try[String] = {
    val confLine = "application.output.json-file"
    val outputFile = Try(conf.getString(confLine))
    checkValue(outputFile, confLine)
  }

  def hasCsvOutput: Try[Boolean] = {
    val confLine =  "application.output.use-csv"
    val use = Try(conf.getBoolean(confLine))
    checkValue(use, confLine)
  }

  def getCsvOutputPath: Try[String] = {
    val confLine =  "application.output.csv-file"
    val outputFile = Try(conf.getString(confLine))
    checkValue(outputFile, confLine)
  }

  def hasYamlOutput: Try[Boolean] = {
    val confLine =  "application.output.use-yaml"
    val use = Try(conf.getBoolean(confLine))
    checkValue(use, confLine)
  }

  def getYamlOutputPath: Try[String] = {
    val confLine =  "application.output.yaml-file"
    val outputFile = Try(conf.getString(confLine))
    checkValue(outputFile, confLine)
  }
}
