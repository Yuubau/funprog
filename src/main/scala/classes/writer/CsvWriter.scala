package classes.writer
import classes.domain.LawnHistory


class CsvWriter(fileName: String) extends FileWriter(fileName) {

  override def writeHeader(): Unit = {
    file.appendLine("numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions")
    ()
  }


  override def writeLawnHistory(lawnHistory: LawnHistory): Unit = {
    for(i <- lawnHistory.oldMowers.indices) {
      file.appendLine(
        (i + 1).toString + ";" + lawnHistory.oldMowers(i).position.x.toString
        + ";" + lawnHistory.oldMowers(i).position.y.toString
        + ";" + lawnHistory.oldMowers(i).orientation.toString
        + ";" + lawnHistory.newMowers(i).position.x.toString
        + ";" + lawnHistory.newMowers(i).position.y.toString
        + ";" + lawnHistory.newMowers(i).orientation.toString
        + ";" + lawnHistory.oldMowers(i).instructions)
    }

  }

  override def writeFooter(): Unit = {
  }
}

object CsvWriter {
  def apply(fileName: String): CsvWriter = new CsvWriter(fileName)


}


