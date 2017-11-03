package org.mycompany.chat.service;


import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.mycompany.chat.domain.Chat;
import org.mycompany.chat.domain.User;
import org.mycompany.chat.repository.ChatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;


    public Chat getChatById(long id) {
        return chatRepository.getOne(id);
    }

    public Chat createChat(long ownerId) {
        User owner = User.builder().id(ownerId).build();
        Chat chat = Chat.builder().members(Sets.newHashSet(owner)).build();
        return chatRepository.save(chat);
    }

    public Page<Chat> getAllChats(Pageable pageable, long memberId) {
        User member = User.builder().id(memberId).build();
        return chatRepository.findAllByMembers(pageable, member);
    }
}
