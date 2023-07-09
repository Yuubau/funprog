import com.typesafe.config.{Config, ConfigFactory}

object AppConfiguration {
  private val conf: Config = ConfigFactory.load()

  def getInputFileName: String = {
    conf.getString("input-file")
  }

  def hasJsonOutput: Boolean = {
    conf.getBoolean("use-json-output")
  }

  def getJsonOutputPath: String = {
    conf.getString("output-json-file")
  }

  def hasCsvOutput: Boolean = {
    conf.getBoolean("use-csv-output")
  }

  def getCsvOutputPath: String = {
    conf.getString("output-csv-file")
  }

  def hasYamlOutput: Boolean = {
    conf.getBoolean("use-json-output")
  }

  def getYamlOutputPath: String = {
    conf.getString("output-json-file")
  }
}
