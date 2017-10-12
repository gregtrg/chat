package org.mycompany.chat.service;

import lombok.RequiredArgsConstructor;
import org.mycompany.chat.domain.Message;
import org.mycompany.chat.repository.ChatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    ChatRepository repository;

    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    public Page<Message> getAllMessages(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
