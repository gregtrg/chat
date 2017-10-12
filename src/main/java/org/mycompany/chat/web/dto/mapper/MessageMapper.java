package org.mycompany.chat.web.dto.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mycompany.chat.domain.Author;
import org.mycompany.chat.domain.Message;
import org.mycompany.chat.web.dto.AuthorDTO;
import org.mycompany.chat.web.dto.MessageDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    MessageDTO toMessageDto(Message message);

    Message toMessage(MessageDTO message);

    AuthorDTO toAuthorDto(Author author);

    Author toAuthor(AuthorDTO author);
}
