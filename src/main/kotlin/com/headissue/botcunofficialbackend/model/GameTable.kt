package com.headissue.botcunofficialbackend.model

data class GameTable(
    val id: String,
    val players: MutableList<Player> = mutableListOf(),
    val turn: Int = 0,
    val isDay: Boolean = turn % 2 == 0
) {
  companion object {
    fun start(gameTable: GameTable): GameTable = GameTable(
        gameTable.id,
        gameTable.players,
        gameTable.turn + 1
    )
  }
}
