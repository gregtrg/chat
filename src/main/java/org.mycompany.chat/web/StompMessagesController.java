package org.mycompany.chat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.repository.ChatRepository;
import org.mycompany.chat.web.dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompMessagesController {

    private final ChatRepository chatRepository;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    @PostMapping("qwerty")
    @ResponseBody
    public MessageDTO messageTo(@RequestBody MessageDTO message) throws Exception {
        chatRepository.saveMessage(message);
        return message;
    }

}
