package org.mycompany.chat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.Message;
import org.mycompany.chat.web.dto.MessageDTO;
import org.mycompany.chat.web.dto.mapper.MessageMapper;
import org.mycompany.chat.service.ChatService;
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

    private final ChatService chatService;

    private final MessageMapper messageMapper;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    @PostMapping("qwerty")
    @ResponseBody
    public MessageDTO messageTo(@RequestBody MessageDTO messageDTO) throws Exception {
        Message message = messageMapper.toMessage(messageDTO);
        message = chatService.saveMessage(message);
        return messageMapper.toMessageDto(message);
    }

}
