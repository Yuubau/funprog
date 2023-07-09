package classes.writer

import classes.Mower

class JsonWriter(fileName: String) extends FileWriter(fileName) {
  def writeHeader(): Unit = {
    file.appendLine("{")
    ()
  }

  def writeLimite(x: Int, y: Int): Unit = {
    file.appendLines(
      "\t\"limite\": {",
      s"\t\t\"x\": ${x.toString},",
      s"\t\t\"y\": ${y.toString}",
      "\t},",
    )
    ()
  }

  def writeMowers(): Unit = {
    file.appendLine("\t\"tondeuses\": [")
    ()
  }

  def writeStartOfMower(mower: Mower): Unit = {
    file.appendLines(
      "\t\t{",
      "\t\t\t\"debut\": {",
      "\"point\": {",
      s"\"x\": ${mower.position.x.toString},",
      s"\"y\": ${mower.position.y.toString}",
      "},",
      s"\"direction\": \"${mower.orientation.toString}\"",
      "},",
      s"\"instructions\": [${instructionsToString(mower.instructions.toList)}],"
    )
    ()
  }

  private def instructionsToString(list: List[Char]): String = list match {
    case inst :: Nil => "\"" + inst.toString + "\""
    case inst :: rest => "\"" + inst.toString + "\"," + instructionsToString(rest)
    case Nil => ""
  }

  def writeEndOfMower(mower: Mower): Unit = {
    file.appendLines(
      "\t\t{",
      "\t\t\t\"fin\": {",
      "\"point\": {",
      s"\"x\": ${mower.position.x.toString},",
      s"\"y\": ${mower.position.y.toString}",
      "},",
      s"\"direction\": \"${mower.orientation.toString}\"",
      "},"
    )
    ()
  }

}

object JsonWriter {
  def apply(fileName: String): JsonWriter = new JsonWriter(fileName)
}
