package org.mycompany.chat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.repository.ChatRepository;
import org.mycompany.chat.web.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@Slf4j
@Controller
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {

    private final ChatRepository chatRepository;

    @GetMapping("/chat")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<MessageDTO> getMessagesByRoomId() {
        return chatRepository.getMessages();
    }
}
