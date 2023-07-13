package classes.writer

import classes.domain.LawnHistory
import play.api.libs.json.Json

class JsonWriter(fileName: String) extends FileWriter(fileName) {
  override def writeHeader(): Unit = {
  }

  override def writeLawnHistory(lawnHistory: LawnHistory): Unit = {
    val mowerChangeJson = Json.toJson(lawnHistory)
    val readableMowerChangeString = Json.prettyPrint(mowerChangeJson)
    file.appendLine(readableMowerChangeString)
    ()
  }

  override def writeFooter(): Unit = {
  }

}

object JsonWriter {
  def apply(fileName: String): JsonWriter = new JsonWriter(fileName)
}
