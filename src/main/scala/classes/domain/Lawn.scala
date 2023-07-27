package classes.domain

import classes.writer.WriterController
import errors.InstructionError

case class Lawn( dimensions: Position, mowers: List[Mower]) {
  import scala.annotation.tailrec

  def execMowers(): Either[List[InstructionError], Lawn] = {
    @tailrec
    def processMowers(mowers: List[Mower], updatedMowers: List[Mower]): Either[List[InstructionError], List[Mower]] = {
      mowers match {
        case Nil => Right(updatedMowers.reverse) // Base case: no more mowers to process, reverse and return the list of updated mowers
        case mower :: remainingMowers =>
          Mower.executeInstructions(mower, dimensions) match {
            case Left(error) => Left(List(error)) // Instruction error, return the error immediately
            case Right(updatedMower) =>
              processMowers(remainingMowers, updatedMower :: updatedMowers) // Tail-recursive call, accumulate the updated mower and continue with the remaining mowers
          }
      }
    }

    processMowers(mowers, Nil) match {
      case Left(errors) => Left(errors) // If there are errors in processing mowers, return the errors.
      case Right(newMowers) =>
        val lawnHistory = LawnHistory(dimensions, mowers, newMowers)
        WriterController.writeHistory(lawnHistory)
        Right(Lawn(dimensions, newMowers))
    }
  }
}
