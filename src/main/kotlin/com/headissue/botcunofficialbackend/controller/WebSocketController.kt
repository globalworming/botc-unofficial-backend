package com.headissue.botcunofficialbackend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller


@Controller
class WebSocketController {

  @Autowired
  lateinit var  messagingTemplate: SimpMessageSendingOperations

  fun updateMessage(id: String) {
    messagingTemplate.convertAndSend("/topic/gameTable/${id}/updates", "{}")
  }


}
