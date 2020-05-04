package com.headissue.botcunofficialbackend.controller

import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class GameTableController {

  @GetMapping("/api/hello")
  fun hello(): String {
    return """
      Hello, the time adsasdasat the server is now ${Date()}
      
      """.trimIndent()
  }
}