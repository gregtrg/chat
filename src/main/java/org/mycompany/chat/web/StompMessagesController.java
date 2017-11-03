package org.mycompany.chat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.UserSecurityDetails;
import org.mycompany.chat.service.ChatValidationService;
import org.mycompany.chat.service.MessageService;
import org.mycompany.chat.web.dto.InviteMessageDTO;
import org.mycompany.chat.web.dto.MessageDTO;
import org.mycompany.chat.web.dto.mapper.MessageMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;

@Slf4j
@Controller
@CrossOrigin
@RequiredArgsConstructor
public class StompMessagesController {

    private final MessageService messageService;

    private final MessageMapper messageMapper;

    private final ChatValidationService validationService;

    @MessageMapping("/socket")
    @SendTo("/topic/messages")
    public MessageDTO sendMessageToChat(
        Principal user,
//                                        long chatId,
                                        MessageDTO messageDTO) throws Exception {
//        //todo web socket Security implementation
//        log.debug("STOMP request to send message to chat id: @ by user: @ ", chatId, user.getEmail());
//        Message message = messageMapper.toEntity(messageDTO);
//        message = messageService.saveMessage(message);
//        return messageMapper.toDto(message);
        return messageDTO;
    }

    @MessageMapping("/chatq")
    @SendTo("/topic/mm")
    public InviteMessageDTO sendInviteMessageToUser(@AuthenticationPrincipal UserSecurityDetails user,
                                                    long addresseeId,
                                                    InviteMessageDTO invite) throws Exception {
        //todo web socket Security implementation
        validationService.validateIfUserHasPermissions(user.getId(), invite.getChatId());
        log.debug("STOMP request to invite user: @ to chat with id: @ by user: @ ",addresseeId, invite.getChatId(), user.getEmail());
        return invite;
    }

}
