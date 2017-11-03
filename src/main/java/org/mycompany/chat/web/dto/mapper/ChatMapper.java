package org.mycompany.chat.web.dto.mapper;

import org.mapstruct.Mapper;
import org.mycompany.chat.domain.Chat;
import org.mycompany.chat.web.dto.ChatDTO;

@Mapper
public interface ChatMapper extends EntityMapper<ChatDTO, Chat> {
}
