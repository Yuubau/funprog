package classes.writer

import better.files._


abstract class FileWriter(fileName: String) {
  import FileWriter._
  val file: File = openFile(fileName).createIfNotExists(asDirectory = false, createParents = true)

  def initFile(): Unit = {
    file.overwrite("")
    ()
  }
}

object FileWriter {
  private def openFile(fileName: String): File = File(fileName)
}
