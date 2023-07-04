package classes

import scala.util.Try

class LawnParser() {

  def parseLawn(content: String): Option[Lawn] = {
    val listString = content.split("\n").toList
    val dimensionsOption = parseLawnDimensions(listString.headOption)
    val mowersOptions = parseMowers(listString.drop(1))

    if (dimensionsOption.isDefined && mowersOptions.forall(_.isDefined)) {
      val mowers = mowersOptions.flatten
      Some(Lawn(dimensionsOption.get, mowers))
    } else {
      None
    }
  }

  def parseLawnDimensions(coordinates: Option[String]): Option[Position] = {
    if (coordinates.nonEmpty) {
      val parsedLine =
        coordinates.get.split(" ").map((x: String) => x.toInt).toList
      Try(Position(parsedLine.head, parsedLine.tail.head)).toOption
    } else {
      None
    }

  }

  def parseMowers(lines: List[String]): List[Option[Mower]] =
    lines.grouped(2).map(parseMower).toList

  def parseMower(line: List[String]): Option[Mower] = {
    val initialPosition = line.head.split(" ").toList
    val x = initialPosition.head.toInt
    val y = initialPosition.tail.head.toInt
    val orientation = initialPosition(2).head
    Try(Mower(Position(x, y), orientation, line(1))).toOption
  }
}
