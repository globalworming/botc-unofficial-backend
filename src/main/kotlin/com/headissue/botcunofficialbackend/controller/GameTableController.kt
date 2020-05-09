package com.headissue.botcunofficialbackend.controller

import com.headissue.botcunofficialbackend.model.GameTable
import com.headissue.botcunofficialbackend.model.GameTableView
import com.headissue.botcunofficialbackend.model.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpSession
import kotlin.NoSuchElementException


@RestController
class GameTableController: BaseRestController() {

  val gameTables = mutableMapOf<String, GameTable>()

  @Autowired lateinit var updateController: WebSocketController

  @GetMapping("/gameTables")
  fun askForGameTables(): ResponseEntity<Collection<GameTable>> {
    return ResponseEntity.ok(gameTables.values)
  }

  @PostMapping("/gameTables")
  fun createNewGameTable(@RequestParam id: String, session: HttpSession): ResponseEntity<GameTableView> {
    val gameTable = GameTable(id, session.id)
    gameTables[id] = gameTable
    return ResponseEntity.ok(GameTableView.of(gameTable).forStoryTeller())
  }

  @GetMapping("/gameTable/{id}")
  fun askForGameTable(@PathVariable id: String, session: HttpSession): ResponseEntity<GameTableView> {
    val gameTable = internalGetTable(id)
    val gameTableView = if (gameTable.storyTeller == session.id)
      GameTableView.of(gameTable).forStoryTeller()
    else GameTableView.of(gameTable).forPlayer(session.id)
    return ResponseEntity.ok(gameTableView)
  }

  @PostMapping("/gameTable/{id}/players")
  fun joinGame(@PathVariable id: String, @RequestParam name: String, session: HttpSession): ResponseEntity<GameTableView> {
    val gameTable = internalGetTable(id)
    gameTable.players.add(Player(name, session.id))
    updateController.updateMessage()
    return ResponseEntity.ok(GameTableView.of(gameTable).forPlayer(session.id))
  }

  @PostMapping("/gameTable/{id}/start")
  fun startGame(@PathVariable id: String): ResponseEntity<GameTableView> {
    val gameTable = GameTable.start(internalGetTable(id))
    gameTables[id] = gameTable
    return ResponseEntity.ok(GameTableView.of(gameTable).forStoryTeller())
  }

  @PostMapping("/gameTable/{id}/nextTurn")
  fun nextTurn(@PathVariable id: String): ResponseEntity<GameTableView> {
    val gameTable = GameTable.nextTurn(internalGetTable(id))
    gameTables[id] = gameTable
    return ResponseEntity.ok(GameTableView.of(gameTable).forStoryTeller())
  }

  @PostMapping("/gameTable/{id}/player/{name}/kill")
  fun killPlayer(@PathVariable id: String, @PathVariable name: String): ResponseEntity<GameTableView> {
    val gameTable = internalGetTable(id)
    val player = gameTable.players.first { it.name == name }
    player.dead = true
    return ResponseEntity.ok(GameTableView.of(gameTable).forStoryTeller())
  }

  @PostMapping("/gameTable/{id}/player/{name}/voted")
  fun markPlayerUsedVote(@PathVariable id: String, @PathVariable name: String): ResponseEntity<GameTableView> {
    val gameTable = internalGetTable(id)
    val player = gameTable.playerNamed(name)
    player.canVote = false
    return ResponseEntity.ok(GameTableView.of(gameTable).forStoryTeller())
  }

  private fun internalGetTable(id: String) = Optional.ofNullable(gameTables[id]).orElseThrow { NoSuchElementException() }

}