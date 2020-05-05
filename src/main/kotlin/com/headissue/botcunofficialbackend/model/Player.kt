package com.headissue.botcunofficialbackend.model

data class Player(
    val name: String = "player",
    var dead: Boolean = false,
    var canVote: Boolean = true
)
