package classes.domain

import classes.writer.WriterController
import errors.InstructionError

case class Lawn( dimensions: Position, mowers: List[Mower]) {

def execMowers(): Either[List[InstructionError], Lawn] = {
  val updatedMowers: List[Either[InstructionError, Mower]] = mowers.map(mower => Mower.execInstructions(mower,dimensions))

  if (updatedMowers.exists(_.isLeft)) {
    val errors = updatedMowers.collect { case Left(error) => error }
    Left(errors)
  } else {
    val newMowers = updatedMowers.collect { case Right(value) => value }
    val lawnHistory = LawnHistory(dimensions, mowers, newMowers)
    WriterController.writeHistory(lawnHistory)
    Right(Lawn(dimensions, newMowers))
  }
}

}
