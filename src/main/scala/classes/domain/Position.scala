package classes.domain

import play.api.libs.json.{JsObject, Json, Writes}

case class Position(x: Int, y: Int)

object Position {
  implicit val positionWrites: Writes[Position] = new Writes[Position] {
    def writes(position: Position): JsObject = Json.obj(
      "x" -> position.x,
      "y" -> position.y
    )
  }
}

