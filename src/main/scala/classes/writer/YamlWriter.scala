package classes.writer
import classes.domain.LawnHistory

class YamlWriter(fileName: String) extends FileWriter(fileName) {
  override def writeHeader(): Unit = {}

  override def writeLawnHistory(lawnHistory: LawnHistory): Unit = {}

  override def writeFooter(): Unit = {}
}

object YamlWriter {
  def apply(fileName: String): YamlWriter = new YamlWriter(fileName)
}