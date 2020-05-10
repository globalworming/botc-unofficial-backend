package com.headissue.botcunofficialbackend.model

import java.util.*
import kotlin.NoSuchElementException

data class GameTable(
    var id: String,
    var storyTeller: String,
    var evilWon: Boolean = false,
    var goodWon: Boolean = false,
    var players: MutableList<Player> = mutableListOf(),
    var turn: Int = 0,
    var isDay: Boolean = turn % 2 == 0
) {


  fun playerNamed(name: String): Player = Optional.ofNullable(players.find { it.name == name })
      .orElseThrow { NoSuchElementException() }

}
