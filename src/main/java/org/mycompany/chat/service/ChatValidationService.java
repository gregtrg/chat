package org.mycompany.chat.service;

import lombok.RequiredArgsConstructor;
import org.mycompany.chat.domain.Chat;
import org.mycompany.chat.domain.User;
import org.mycompany.chat.repository.ChatRepository;
import org.mycompany.chat.validation.exception.SecurityValidationException;
import org.springframework.stereotype.Service;

import static org.mycompany.chat.validation.ValidationMessages.USER_IS_NOT_MEMBER_OF_CHAT;

@Service
@RequiredArgsConstructor
public class ChatValidationService {

    private final ChatRepository chatRepository;

    public void validateIfUserHasPermissions(long userId, long chatId) {
        Chat chat = chatRepository.findOne(chatId);
        if(userIsNotMemberOfChat(userId, chat)) {
            throw new SecurityValidationException(USER_IS_NOT_MEMBER_OF_CHAT);
        }
    }

    private boolean userIsNotMemberOfChat(long userId, Chat chat) {
        return chat.getMembers().stream()
            .map(User::getId)
            .noneMatch(id -> id == userId);
    }
}
