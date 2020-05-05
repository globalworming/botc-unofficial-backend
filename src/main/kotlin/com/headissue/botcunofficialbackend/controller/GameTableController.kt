package com.headissue.botcunofficialbackend.controller

import com.headissue.botcunofficialbackend.model.GameTable
import com.headissue.botcunofficialbackend.model.Player
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.NoSuchElementException


@RestController
class GameTableController {

  val gameTables = mutableMapOf<String, GameTable>()

  @GetMapping("/gametables")
  fun askForGameTables(): ResponseEntity<Collection<GameTable>> {
    return ResponseEntity.ok(gameTables.values)
  }

  @PostMapping("/gametables")
  fun createNewGameTable(@RequestParam id: String): ResponseEntity<GameTable> {
    val gameTable = GameTable(id)
    gameTables[id] = gameTable
    return ResponseEntity.ok(gameTable)
  }

  @GetMapping("/gametable/{id}")
  fun askForGameTable(@PathVariable id: String): ResponseEntity<GameTable> {
    return ResponseEntity.ok(internalGetTable(id))
  }

  @PostMapping("/gametable/{id}/players")
  fun joinGame(@PathVariable id: String, @RequestParam name: String): ResponseEntity<GameTable> {
    val gameTable = internalGetTable(id)
    gameTable.players.add(Player(name))
    return ResponseEntity.ok(gameTable)
  }

  @PostMapping("/gametable/{id}/start")
  fun startGame(@PathVariable id: String): ResponseEntity<GameTable> {
    val gameTable = GameTable.start(internalGetTable(id))
    gameTables[id] = gameTable
    return ResponseEntity.ok(gameTable)
  }

  private fun internalGetTable(id: String) = Optional.ofNullable(gameTables[id]).orElseThrow { NoSuchElementException() }

}