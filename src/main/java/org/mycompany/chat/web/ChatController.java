package org.mycompany.chat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.Message;
import org.mycompany.chat.web.dto.mapper.MessageMapper;
import org.mycompany.chat.web.util.PaginationUtil;
import org.mycompany.chat.service.ChatService;
import org.mycompany.chat.web.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    SecurityContext securityContext;

    private final ChatService chatService;

    private final MessageMapper messageMapper;

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<MessageDTO> getMessagesByRoomId(@AuthenticationPrincipal User user) {
//        return  chatRepository.getMessages().stream()
//                .map(messageMapper.INSTANCE::messageToMessageDto)
//            .collect(Collectors.toList());
//    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MessageDTO>> getMessagesByRoomId(Pageable pageable) {
        Page<Message> page = chatService.getAllMessages(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/chat");
        List<MessageDTO> messages = page.getContent().stream()
            .map(messageMapper::toMessageDto)
            .collect(Collectors.toList());
        return new ResponseEntity<>(messages, headers, HttpStatus.OK);
    }
}
