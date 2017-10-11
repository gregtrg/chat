package org.mycompany.chat.web.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mycompany.chat.domain.Message;
import org.mycompany.chat.web.dto.MessageDTO;

@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper( MessageMapper.class );
    MessageDTO messageToMessageDto(Message message);
}
