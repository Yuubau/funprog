package classes.domain

import errors.InstructionError
import play.api.libs.json.{JsObject, Json, Writes}

import scala.annotation.tailrec


case class Mower(
    position: Position,
    orientation: Char,
    instructions: String
)

object Mower {

  def execInstructions(mower: Mower, lawnLimite: Position): Either[InstructionError, Mower] = {

    @tailrec
    def execInstructionsHelper(mower: Mower, instructionsList: List[Char], lawnLimite: Position): Either[InstructionError, Mower] = {
      instructionsList match {
        case head :: tail => {
          val resInstruction = execInstruction(mower, head, lawnLimite);
          resInstruction match {
            case Left(value) => Left(value)
            case Right(value) => execInstructionsHelper(value, tail, lawnLimite)
          }
        }
        case Nil => Right(mower)
      }
    }

    execInstructionsHelper(mower, mower.instructions.toList, lawnLimite)

  }

  def execInstruction(mower: Mower, instruction: Char, lawnLimite: Position): Either[InstructionError, Mower] = {
    instruction match {
      case 'D' | 'G' => Right(turn(mower, instruction).map(_.copy())).flatten
      case 'A' => Right(moveForward(mower, lawnLimite).map(_.copy())).flatten
      case _ =>
        Left(InstructionError("Invalid Instruction", instruction.toString))
    }
  }



  def executeInstructions(mower: Mower, dimensions: Position ): Either[InstructionError, Mower] = {
    mower.instructions.foldLeft[Either[InstructionError, Mower]](Right(mower)) {
      (mowerResult, instruction) =>
        mowerResult.flatMap { currentMower =>
          executeInstruction(currentMower, instruction,dimensions)
        }
    }
  }

  private def executeInstruction(
      mower: Mower,
      instruction: Char,
      dimension:Position): Either[InstructionError, Mower] = {
    instruction match {
      case 'D' | 'G' => Right(turn(mower, instruction).map(_.copy())).flatten
      case 'A'       => Right(moveForward(mower,dimension).map(_.copy())).flatten
      case _ =>
        Left(InstructionError("Invalid Instruction", instruction.toString))
    }
  }

  private def turn(
      mower: Mower,
      direction: Char): Either[InstructionError, Mower] = direction match {
    case 'D' =>
      mower.orientation match {
        case 'N' => Right(mower.copy(orientation = 'E'))
        case 'E' => Right(mower.copy(orientation = 'S'))
        case 'S' => Right(mower.copy(orientation = 'W'))
        case 'W' => Right(mower.copy(orientation = 'N'))
        case _ =>
          Left(
            InstructionError("Invalid Orientation", mower.orientation.toString)
          )
      }

    case 'G' =>
      mower.orientation match {
        case 'N' => Right(mower.copy(orientation = 'W'))
        case 'W' => Right(mower.copy(orientation = 'S'))
        case 'S' => Right(mower.copy(orientation = 'E'))
        case 'E' => Right(mower.copy(orientation = 'N'))
        case _ =>
          Left(
            InstructionError("Invalid Orientation", mower.orientation.toString)
          )
      }

    case _ => Left(InstructionError("Invalid Direction", direction.toString))
  }

  private def moveForward(mower: Mower,dimension : Position): Either[InstructionError, Mower] = {
    val newPosition: Either[InstructionError, Position] = mower.orientation match {
      case 'N' => Right(Position(mower.position.x, mower.position.y + 1))
      case 'E' => Right(Position(mower.position.x + 1, mower.position.y))
      case 'S' => Right(Position(mower.position.x, mower.position.y - 1))
      case 'W' => Right(Position(mower.position.x - 1, mower.position.y))
      case _ => Left(InstructionError("Invalid Orientation", mower.orientation.toString))
    }
    newPosition match {
      case Right(position : Position) =>
        if (isInsideLawn(position, dimension)) {
          Right(Mower(position = position, orientation = mower.orientation, instructions = mower.instructions))
        }else{
          Right(mower)
        }
      case Left(_) => Left(InstructionError("Invalid orientation", mower.instructions))
    }
  }

  def isInsideLawn(position: Position, dimensions: Position): Boolean =
    position.x >= 0 && position.x <= dimensions.x &&
      position.y >= 0 && position.y <= dimensions.y

  implicit val mowerWrites: Writes[Mower] = new Writes[Mower] {
    def writes(mower: Mower): JsObject = Json.obj(
      "point" -> mower.position,
      "orientation" -> mower.orientation.toString
    )
  }
}


