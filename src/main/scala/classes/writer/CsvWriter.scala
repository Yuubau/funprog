package classes.writer

class CsvWriter(fileName: String) extends FileWriter(fileName) {

  def writeHeader(): Unit = {
    file.appendLine("numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions")
    ()
  }
}

object CsvWriter {
  def apply(fileName: String): CsvWriter = new CsvWriter(fileName)
}
