package com.headissue.botcunofficialbackend.controller

import com.headissue.botcunofficialbackend.model.GameTable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping

import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class GameTableController {

  @PostMapping("/gametables", produces = ["application/json"])
  fun createNewGameTable(): ResponseEntity<Any> {
    return ResponseEntity.ok(GameTable())
  }
}