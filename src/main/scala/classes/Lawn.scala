package classes

case class Lawn(dimensions: Position, mowers: List[Mower]) {

//  def execMowers(): Option[Lawn] = {
//    val updatedMowers = mowers.flatMap { mower =>
//      executeAllInstructions(mower, mower.instructions)
//    }
//  }

  def isInsideLawn(position: Position): Boolean =
    position.x >= 0 && position.x <= dimensions.x &&
      position.y >= 0 && position.y <= dimensions.y

}
