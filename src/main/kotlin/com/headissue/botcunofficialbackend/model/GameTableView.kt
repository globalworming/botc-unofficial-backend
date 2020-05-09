package com.headissue.botcunofficialbackend.model

data class GameTableView(
    val id: String,
    val players: List<Player>,
    val turn: Int,
    val isDay: Boolean = turn % 2 == 0,
    val isStoryTeller: Boolean,
    val you: String
) {
  companion object {
    fun of(gameTable: GameTable): GameTableViewFactory = GameTableViewFactory(gameTable)
  }
}

class GameTableViewFactory(var gameTable: GameTable) {

  fun forStoryTeller(): GameTableView = GameTableView(
      id = gameTable.id,
      players = gameTable.players,
      turn = gameTable.turn,
      isStoryTeller = true,
      you = "storyteller"
  )

  fun forPlayer(sessionId: String): GameTableView = GameTableView(
      id = gameTable.id,
      players = gameTable.players,
      turn = gameTable.turn,
      isStoryTeller = false,
      you = playerNameFor(sessionId, gameTable)
  )

  private fun playerNameFor(sessionId: String, gameTable: GameTable): String {
    val player = gameTable.players.firstOrNull { it.sessionId == sessionId }
    return player?.name ?: ""
  }

}
