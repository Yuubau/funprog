package classes.domain

import play.api.libs.json.{JsObject, Json, Writes}


case class MowerHistory(oldMower: Mower, newMower: Mower)

object MowerHistory {

  implicit val positionWrites: Writes[MowerHistory] = new Writes[MowerHistory] {
    def writes(mowerHistory: MowerHistory): JsObject = Json.obj(
      "debut" -> mowerHistory.oldMower,
      "instructions" -> mowerHistory.oldMower.instructions.toList.map(c => c.toString),
      "fin" -> mowerHistory.newMower
    )
  }
}