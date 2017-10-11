package org.mycompany.chat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.Author;
import org.mycompany.chat.repository.ChatRepository;
import org.mycompany.chat.web.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    SecurityContext securityContext;

    private final ChatRepository chatRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDTO> getMessagesByRoomId(@AuthenticationPrincipal Author user) {
        return chatRepository.getMessages();
    }
}
