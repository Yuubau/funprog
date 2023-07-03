package classes

import errors.InstructionError

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
