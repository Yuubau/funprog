package classes.writer

import better.files._
import classes.domain.LawnHistory

abstract class FileWriter(fileName: String) {
  import FileWriter._
  val file: File = openFile(fileName).createIfNotExists(asDirectory = false, createParents = true)

  def initFile(): Unit = {
    clearFile()
    writeHeader()
  }

  private def clearFile(): Unit = {
    file.overwrite("")
    ()
  }

  def writeHeader(): Unit

  def writeLawnHistory(lawnHistory: LawnHistory): Unit

  def writeFooter(): Unit

}

object FileWriter {
  private def openFile(fileName: String): File = File(fileName)
}
