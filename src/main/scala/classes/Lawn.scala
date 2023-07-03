package classes

case class Lawn(dimensions: Position, mowers: List[Mower]) {

  def isInsideLawn(position: Position): Boolean =
    position.x >= 0 && position.x <= dimensions.x &&
      position.y >= 0 && position.y <= dimensions.y

}
