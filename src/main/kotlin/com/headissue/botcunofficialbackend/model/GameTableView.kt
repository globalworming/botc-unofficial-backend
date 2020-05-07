package com.headissue.botcunofficialbackend.model

data class GameTableView(
    val id: String,
    val players: List<Player>,
    val turn: Int,
    val isDay: Boolean = turn % 2 == 0,
    val storyTeller: Boolean
) {
  companion object {
    fun of(gameTable: GameTable): GameTableViewBuilder = GameTableViewBuilder(gameTable = gameTable)
  }
}

class GameTableViewBuilder(var gameTable: GameTable? = null) {
  fun forStoryTeller(): GameTableView = GameTableView(
      id = gameTable!!.id,
      players = gameTable!!.players,
      turn = gameTable!!.turn,
      storyTeller = true
  )

  fun forPlayer(): GameTableView = GameTableView(
      id = gameTable!!.id,
      players = gameTable!!.players,
      turn = gameTable!!.turn,
      storyTeller = false
  )

}
