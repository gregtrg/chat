package org.mycompany.chat.repository;
import org.mycompany.chat.web.dto.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ChatRepository {

    private final List<MessageDTO> allMessages = new CopyOnWriteArrayList<>();

    public List<MessageDTO> getMessages() {
        return allMessages;
    }

    public boolean saveMessage(MessageDTO message) {
        return allMessages.add(message);
    }

}
