package com.headissue.botcunofficialbackend.controller

import com.headissue.botcunofficialbackend.model.GameTable
import com.headissue.botcunofficialbackend.model.GameTableView
import com.headissue.botcunofficialbackend.model.Player
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpSession
import kotlin.NoSuchElementException


@RestController
class GameTableController {

  val gameTables = mutableMapOf<String, GameTable>()

  @GetMapping("/gameTables")
  fun askForGameTables(): ResponseEntity<Collection<GameTable>> {
    return ResponseEntity.ok(gameTables.values)
  }

  @PostMapping("/gameTables")
  fun createNewGameTable(@RequestParam id: String, session: HttpSession): ResponseEntity<GameTable> {
    val gameTable = GameTable(id = id, storyTeller = session.id)
    gameTables[id] = gameTable
    return ResponseEntity.ok(gameTable)
  }

  @GetMapping("/gameTable/{id}")
  fun askForGameTable(@PathVariable id: String, session: HttpSession): ResponseEntity<out GameTableView> {
    val gameTable = internalGetTable(id)
    val gameTableView = if (gameTable.storyTeller == session.id) GameTableView.of(gameTable).forStoryTeller() else GameTableView.of(gameTable).forPlayer()
    return ResponseEntity.ok(gameTableView)
  }

  @PostMapping("/gameTable/{id}/players")
  fun joinGame(@PathVariable id: String, @RequestParam name: String): ResponseEntity<GameTable> {
    val gameTable = internalGetTable(id)
    gameTable.players.add(Player(name))
    return ResponseEntity.ok(gameTable)
  }

  @PostMapping("/gameTable/{id}/start")
  fun startGame(@PathVariable id: String): ResponseEntity<GameTable> {
    val gameTable = GameTable.start(internalGetTable(id))
    gameTables[id] = gameTable
    return ResponseEntity.ok(gameTable)
  }

  @PostMapping("/gameTable/{id}/nextTurn")
  fun nextTurn(@PathVariable id: String): ResponseEntity<GameTable> {
    val gameTable = GameTable.nextTurn(internalGetTable(id))
    gameTables[id] = gameTable
    return ResponseEntity.ok(gameTable)
  }

  @PostMapping("/gameTable/{id}/player/{name}/kill")
  fun killPlayer(@PathVariable id: String, @PathVariable name: String): ResponseEntity<GameTable> {
    val gameTable = internalGetTable(id)
    val player = Optional.ofNullable(gameTable.players.find { it.name == name }).orElseThrow { NoSuchElementException() }
    player.dead = true
    return ResponseEntity.ok(gameTable)
  }

  @PostMapping("/gameTable/{id}/player/{name}/voted")
  fun markPlayerUsedVote(@PathVariable id: String, @PathVariable name: String): ResponseEntity<GameTable> {
    val gameTable = internalGetTable(id)
    val player = gameTable.playerNamed(name)
    player.canVote = false
    return ResponseEntity.ok(gameTable)
  }

  private fun internalGetTable(id: String) = Optional.ofNullable(gameTables[id]).orElseThrow { NoSuchElementException() }

}