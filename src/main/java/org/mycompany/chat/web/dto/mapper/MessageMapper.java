package org.mycompany.chat.web.dto.mapper;


import org.mapstruct.Mapper;
import org.mycompany.chat.domain.Message;
import org.mycompany.chat.domain.UserSecurityDetails;
import org.mycompany.chat.web.dto.MessageDTO;
import org.mycompany.chat.web.dto.UserDTO;

//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Mapper
public interface MessageMapper {

    MessageDTO toMessageDto(Message message);

    Message toMessage(MessageDTO message);

    UserDTO toUserDto(UserSecurityDetails userSecurityDetails);

    UserSecurityDetails toUser(UserDTO user);
}
