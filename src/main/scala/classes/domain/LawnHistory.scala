package classes.domain

import play.api.libs.json.{JsObject, Json, Writes}

case class LawnHistory (
  limit: Position,
  oldMowers: List[Mower],
  newMowers: List[Mower]
) {
  val mowerHistories: List[MowerHistory] = (oldMowers zip newMowers).map{ case (oldM, newM) => MowerHistory(oldM, newM)}
}

object LawnHistory
{
  def apply(
             limit: Position,
             oldMowers: List[Mower],
             newMowers: List[Mower]
           ): LawnHistory = new LawnHistory(limit, oldMowers, newMowers)

  implicit val lawnHistoryWrites: Writes[LawnHistory] = new Writes[LawnHistory] {
    def writes(lawnHistory: LawnHistory): JsObject = Json.obj(
      "limite" -> lawnHistory.limit,
      "tondeuses" -> lawnHistory.mowerHistories
    )
  }
}
