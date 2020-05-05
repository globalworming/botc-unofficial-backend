package com.headissue.botcunofficialbackend.model

import java.util.*

data class GameTable(
    val id: UUID,
    val name: String,
    val players: List<Player> = listOf()
)
