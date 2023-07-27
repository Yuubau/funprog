package classes.domain

import classes.writer.WriterController
import errors.InstructionError

case class Lawn( dimensions: Position, mowers: List[Mower]) {
  import scala.annotation.tailrec

  def execMowers(): Either[List[InstructionError], Lawn] = {
    @tailrec
    def processMowers(mowers: List[Mower], updatedMowers: List[Mower]): Either[List[InstructionError], List[Mower]] = {
      mowers match {
        case Nil => Right(updatedMowers.reverse)
        case mower :: remainingMowers =>
          Mower.execInstructions(mower, dimensions) match {
            case Left(error) => Left(List(error))
            case Right(updatedMower) =>
              processMowers(remainingMowers, updatedMower :: updatedMowers)
          }
      }
    }

    processMowers(mowers, Nil) match {
      case Left(errors) => Left(errors)
      case Right(newMowers) =>
        val lawnHistory = LawnHistory(dimensions, mowers, newMowers)
        WriterController.initWriters()
        WriterController.writeHistory(lawnHistory)
        Right(Lawn(dimensions, newMowers))
    }
  }
}
