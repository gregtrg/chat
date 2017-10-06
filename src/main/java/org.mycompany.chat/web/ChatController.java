package org.mycompany.chat.web;

import lombok.RequiredArgsConstructor;
import org.mycompany.chat.repository.ChatRepository;
import org.mycompany.chat.web.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {

    private final ChatRepository chatRepository;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    @PostMapping("qwerty")
    @ResponseBody
    public MessageDTO messageTo(@RequestBody MessageDTO message) throws Exception {
        chatRepository.saveMessage(message);
        return message;
    }


    @GetMapping("/chat")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<MessageDTO> getMessagesByRoomId() {
        return chatRepository.getMessages();
    }

}
