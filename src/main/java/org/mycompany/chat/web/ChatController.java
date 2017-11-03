package org.mycompany.chat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.Chat;
import org.mycompany.chat.domain.UserSecurityDetails;
import org.mycompany.chat.service.ChatService;
import org.mycompany.chat.service.ChatValidationService;
import org.mycompany.chat.web.dto.ChatDTO;
import org.mycompany.chat.web.dto.mapper.ChatMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final ChatValidationService validationService;
    private final ChatMapper chatMapper;

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<MessageDTO> getMessagesByRoomId(@AuthenticationPrincipal UserSecurityDetails user) {
//        return  chatRepository.getMessages().stream()
//                .map(messageMapper.INSTANCE::messageToMessageDto)
//            .collect(Collectors.toList());
//    }

    //    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<List<MessageDTO>> getMessagesByRoomId(Pageable pageable) {
//        Page<Message> page = chatService.getAllMessages(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/chat");
//        List<MessageDTO> messages = page.getContent().stream()
//            .map(messageMapper::toMessageDto)
//            .collect(Collectors.toList());
//        return new ResponseEntity<>(messages, headers, HttpStatus.OK);
//    }
//    @GetMapping(path = "/{chat_id}")
//    @ResponseStatus(HttpStatus.OK)
//    public ChatDTO getChat(@AuthenticationPrincipal UserSecurityDetails user,
//                           @PathParam("chat_id") Long chatId) {
//        log.debug("REST request to get chat with id: @ by user: @ ", chatId, user.getEmail());
//        validationService.validateIfUserHasPermissions(user.getId(), chatId);
//        Chat chat = chatService.getChatById(chatId);
//        return chatMapper.toDto(chat);
//    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChatDTO> getAllChatsWhereMember(@AuthenticationPrincipal UserSecurityDetails user, Pageable pageable) {
        log.debug("REST request to get all chat where member by user: @", user.getEmail());
        Page<Chat> chats = chatService.getAllChats(pageable, user.getId());
        return chatMapper.toDto(chats.getContent());
    }

    @PostMapping
    public ResponseEntity<ChatDTO> createChat(@AuthenticationPrincipal UserSecurityDetails user) throws URISyntaxException {
        log.debug("REST request to create chat by user: @ ", user.getEmail());
        Chat chat = chatService.createChat(user.getId());
        ChatDTO result = chatMapper.toDto(chat);
        return ResponseEntity.created(new URI("/chat/" + chat.getId()))
            .body(result);
    }

}
