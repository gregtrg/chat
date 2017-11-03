package org.mycompany.chat.web.dto;

import java.util.Set;

public class ChatDTO {
    private long id;

    private Set<UserDTO> members;

    private Set<MessageDTO> messages;
}
