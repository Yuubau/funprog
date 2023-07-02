import scala.util.{Failure, Success, Try}
import better.files.File

import java.io.File

sealed trait Error

case class ParsingError(message: String) extends Error

case class InstructionError(message: String, orientation: String) extends Error

case class Position(x: Int, y: Int)

case class Lawn(coordinates: Position)

case class Mower(
                  position: Position,
                  orientation: Char,
                  instructions: String
                )

object Mower {

  def executeInstruction(mower: Mower, instruction: Char): Either[InstructionError, Mower] = {
    instruction match {
      case 'D' | 'G' => Right(turn (mower, instruction).map(_.copy())).flatten
      case 'A' => Right(moveForward(mower).map(_.copy())).flatten
      case _ => Left(InstructionError("Invalid Instruction", instruction.toString))
    }
  }

  private def turn(mower: Mower, direction: Char): Either[InstructionError, Mower] = direction match {
    case 'D' =>
      mower.orientation match {
        case 'N' => Right(mower.copy(orientation = 'E'))
        case 'E' => Right(mower.copy(orientation = 'S'))
        case 'S' => Right(mower.copy(orientation = 'W'))
        case 'W' => Right(mower.copy(orientation = 'N'))
        case _ => Left(InstructionError("Invalid Orientation", mower.orientation.toString))
      }

    case 'G' =>
      mower.orientation match {
        case 'N' => Right(mower.copy(orientation = 'W'))
        case 'W' => Right(mower.copy(orientation = 'S'))
        case 'S' => Right(mower.copy(orientation = 'E'))
        case 'E' => Right(mower.copy(orientation = 'N'))
        case _ => Left(InstructionError("Invalid Orientation", mower.orientation.toString))
      }

    case _ => Left(InstructionError("Invalid Direction", direction.toString))
  }

  private def moveForward(mower: Mower): Either[InstructionError, Mower] = {
    mower.orientation match {
      case 'N' => Right(mower.copy(position =Position(mower.position.x, mower.position.y + 1)))
      case 'E' => Right(mower.copy(position =Position(mower.position.x + 1, mower.position.y)))
      case 'S' => Right(mower.copy(position =Position(mower.position.x, mower.position.y - 1)))
      case 'W' => Right(mower.copy(position =Position(mower.position.x - 1, mower.position.y)))
      case _ => Left(InstructionError("Invalid Orientation", mower.orientation.toString))
    }
  }
}

object Main extends App {
//  val inputContent =
//    """5 5
//      |1 2 N
//      |GAGAGAGAA
//      |3 3 E
//      |AADAADADDA""".stripMargin
  val path = "../ressources/mower.txt"
  val lines = readFile(path)=
    lines match {
      case Success(listString: String) => listString
      case Failure(e) =>
        println("Erreur de lecture du fichier.")
        List.empty[String]
    }

  val listLine = lines.split("\n").toList
  val lawn = parseLawnCoordinates(listLine.head) match {
    case Success(lawnObj) => lawnObj
    case Failure(e) =>
      println("Erreur de parsing des coordonnées de la pelouse.")
      Failure(e)
  }
  println(lawn)

  val mowerList = parseMowers(lines.drop(1)) match {
    case Success(parsedMowers) => parsedMowers
    case Failure(e) =>
      println("Erreur de parsing des coordonnées des tondeuses.")
      Failure(e)
  }


  println(mowerList)

  def readFile(pathFile: String): Try[List[String]] = Try {
    val file = File("ressources/mower.txt")
    file.lines.toList
  }

  def readFile(pathFile : String):Try[String] = Try{
    val file = File("ressources/mower.txt")
    file.lines.toString()
  }

  def parseLawnCoordinates(coordinates: String): Try[Lawn] = Try {
    val parsedLine = coordinates.split(" ").map((x: String) => x.toInt).toList
    Lawn(Position(parsedLine.head, parsedLine.tail.head))
  }

  def parseMowers(lines: List[String]): Try[List[Mower]] = Try {
    lines.grouped(2).map(parseMower).toList
  }

  def parseMower(line: List[String]): Mower = {
    val initialPosition = line.head.split(" ").toList
    val x = initialPosition.head.toInt
    val y = initialPosition.tail.head.toInt
    val orientation = initialPosition(2).head
    Mower(
      Position(x, y),
      orientation,
      line(1)
    )
  }
  def isInsideLawn(position: Position, lawn: Lawn): Boolean =
    position.x >= 0 && position.x <= lawn.coordinates.x &&
      position.y >= 0 && position.y <= lawn.coordinates.y
}