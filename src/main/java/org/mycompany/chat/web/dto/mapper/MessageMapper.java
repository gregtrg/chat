package org.mycompany.chat.web.dto.mapper;


import org.mapstruct.Mapper;
import org.mycompany.chat.domain.Message;
import org.mycompany.chat.web.dto.MessageDTO;

//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Mapper
public interface MessageMapper extends EntityMapper <MessageDTO, Message>{
}
