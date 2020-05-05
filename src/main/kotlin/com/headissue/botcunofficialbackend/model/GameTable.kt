package com.headissue.botcunofficialbackend.model

import java.util.*

data class GameTable(
    val id: String,
    val players: MutableList<Player> = mutableListOf()
)
