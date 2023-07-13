package classes.parser

import classes.domain.Mower.isInsideLawn
import classes.domain.{Lawn, Mower, Position}

import scala.util.{Failure, Success, Try}

class LawnParser() {

  def parseLawn(content: String): Option[Lawn] = {
    val listString = content.split("\r\n").toList
    val dimensionsOption : Option[Position] = listString.headOption.flatMap(parseLawnDimensions)
    val tailFile = listString.drop(1)
    val mowersOptions : List[Option[Mower]] = tailFile.grouped(2).map(parseMower).toList

    if (mowersOptions.forall(mower =>mower.isDefined)) {
      val mowers = mowersOptions.flatten
      dimensionsOption match {
        case Some(dimensions) => if (mowers.forall(mower => isInsideLawn(mower.position, dimensions))) {
          Some(Lawn(dimensions, mowers))
        } else {
          None
        }
        case None => None
      }
    } else {
      None
    }
  }

  def parseLawnDimensions(coordinates: String): Option[Position] = {
    if (coordinates.nonEmpty) {
        val parsedLine: Try[List[Int]] = Try(coordinates.split(" ").map(str => str.toInt).toList)
        parsedLine match {
          case Success(line)=>
            val xPosition: Option[Int] = line.headOption
            val yPosition: Option[Int] = line.drop(1).headOption
            (xPosition, yPosition) match {
              case (Some (x), Some (y) ) => Some (Position (x, y) )
              case _ => None
            }
          case Failure(_) => None
        }
    } else {
      None
    }
  }

  def parseMower(lines: List[String]): Option[Mower] = {
    val initialPosition : Option[List[String]] =  lines.headOption.flatMap(str => Option(str.split(" ").toList))
    val instructions : Option[String] = lines.lift(1)
    (initialPosition,instructions) match {
      case (Some(position), Some(inst))=>
        val xPosition : Option[Int] = position.headOption.flatMap(x => Option(x.toInt))
        val yPosition : Option[Int] = position.lift(1).flatMap(y => Option(y.toInt))
        val orientation : Option[Char] = Option(position(2)(0))
        (xPosition,yPosition,orientation,inst) match {
          case (Some(x), Some(y),Some(o),i) => Option(Mower(Position(x, y), o,i))
          case _ => None
        }
      case _ => None
    }

  }
}
