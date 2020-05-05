package com.headissue.botcunofficialbackend.controller

import com.headissue.botcunofficialbackend.model.GameTable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class GameTableController {

  val gameTables = mutableMapOf<UUID, GameTable>()

  @PostMapping("/gametables")
  fun createNewGameTable(@RequestParam name: String): ResponseEntity<Any> {
    val uuid = UUID.randomUUID()
    val gameTable = GameTable(uuid, name)
    gameTables[uuid] = gameTable
    return ResponseEntity.ok(gameTable)
  }
}