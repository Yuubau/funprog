package classes.writer
import classes.domain.LawnHistory

class CsvWriter(fileName: String) extends FileWriter(fileName) {

  override def writeHeader(): Unit = {
    file.appendLine("numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions")
    ()
  }

  override def writeLawnHistory(lawnHistory: LawnHistory): Unit = {}

  override def writeFooter(): Unit = {}
}

object CsvWriter {
  def apply(fileName: String): CsvWriter = new CsvWriter(fileName)
}
