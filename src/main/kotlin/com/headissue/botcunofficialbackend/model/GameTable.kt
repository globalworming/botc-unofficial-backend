package com.headissue.botcunofficialbackend.model

import java.util.*
import kotlin.NoSuchElementException

data class GameTable(
    val id: String,
    val players: MutableList<Player> = mutableListOf(),
    val turn: Int = 0,
    val isDay: Boolean = turn % 2 == 0
) {

  fun playerNamed(name: String): Player = Optional.ofNullable(players.find { it.name == name })
      .orElseThrow { NoSuchElementException() }

  companion object {
    fun start(gameTable: GameTable): GameTable = nextTurn(gameTable)

    fun nextTurn(gameTable: GameTable): GameTable = GameTable(
        gameTable.id,
        gameTable.players,
        gameTable.turn + 1
    )
  }
}
