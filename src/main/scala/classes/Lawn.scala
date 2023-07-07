package classes

import errors.InstructionError

case class Lawn( dimensions: Position, mowers: List[Mower]) {

def execMowers(): Either[List[InstructionError], Lawn] = {
val updatedMowers: List[Either[InstructionError, Mower]] = mowers.map(mower => Mower.executeInstructions(mower,dimensions))

  if (updatedMowers.exists(_.isLeft)) {
    val errors = updatedMowers.collect { case Left(error) => error }
    Left(errors)
  } else {
    val newMowers = updatedMowers.collect { case Right(value) => value }
    Right(Lawn(dimensions, newMowers))
  }
}




}
