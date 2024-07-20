package berdibekov.de.backend.mapper;

import berdibekov.de.backend.dto.MessageDTO;
import berdibekov.de.backend.model.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageDTOMapper {

    MessageDTO toDTO(Message message);

    Message toModel(MessageDTO messageDTO);

    List<Message> toMessages(List<MessageDTO> messageDTOS);

    List<MessageDTO> toMessagesDTOs(List<Message> messages);

}
