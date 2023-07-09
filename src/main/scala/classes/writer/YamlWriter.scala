package classes.writer

class YamlWriter(fileName: String) extends FileWriter(fileName) {

}

object YamlWriter {
  def apply(fileName: String): YamlWriter = new YamlWriter(fileName)
}