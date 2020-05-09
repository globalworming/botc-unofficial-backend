package com.headissue.botcunofficialbackend.model

data class Player(
    val name: String = "player",
    val sessionId: String,
    var dead: Boolean = false,
    var canVote: Boolean = true
)