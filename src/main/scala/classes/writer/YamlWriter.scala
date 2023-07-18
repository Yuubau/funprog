package classes.writer
import classes.domain.LawnHistory

class YamlWriter(fileName: String) extends FileWriter(fileName) {
  override def writeHeader(): Unit = {
    file.appendLine("limite:")
    ()
  }

  override def writeLawnHistory(lawnHistory: LawnHistory): Unit = {
    file.appendLine("  x: " + lawnHistory.limit.x.toString)
    file.appendLine("  y: " + lawnHistory.limit.y.toString)
    file.appendLine("tondeuses:")
    for(i <- lawnHistory.oldMowers.indices) {
      file.appendLine("  - debut:")
      file.appendLine("      point:")
      file.appendLine("        x: " + lawnHistory.oldMowers(i).position.x.toString)
      file.appendLine("        y: " + lawnHistory.oldMowers(i).position.y.toString)
      file.appendLine("      direction: " + lawnHistory.oldMowers(i).orientation.toString)
      file.appendLine("    instructions: ")
      for(j <- lawnHistory.oldMowers(i).instructions.split("").indices) {
        file.appendLine("      - " + lawnHistory.oldMowers(i).instructions.split("")(j))
      }
      file.appendLine("    fin:")
      file.appendLine("      point:")
      file.appendLine("        x: " + lawnHistory.newMowers(i).position.x.toString)
      file.appendLine("        y: " + lawnHistory.newMowers(i).position.y.toString)
      file.appendLine("      direction: " + lawnHistory.newMowers(i).orientation.toString)

    }
  }

  override def writeFooter(): Unit = {}
}

object YamlWriter {
  def apply(fileName: String): YamlWriter = new YamlWriter(fileName)
}