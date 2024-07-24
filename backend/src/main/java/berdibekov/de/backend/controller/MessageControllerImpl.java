package berdibekov.de.backend.controller;

import berdibekov.de.backend.dto.MessageDTO;
import berdibekov.de.backend.mapper.MessageDTOMapper;
import berdibekov.de.backend.model.Message;
import berdibekov.de.backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController{

    private final MessageService messageService;
    private final MessageDTOMapper messageDTOMapper;

    @Override
    public MessageDTO createMessage(String personId, @RequestBody MessageDTO messageDTO) {
        log.info("Received request to create message: {}", messageDTO);

        // map to the domain model
        Message message = messageDTOMapper.toModel(messageDTO);

        // create a message
        Message createdMessage = messageService.createMessage(personId, message);

        log.info("A new message with id {} was created", createdMessage.getId());

        // map back to the DTO and return
        return messageDTOMapper.toDTO(createdMessage, personId);
    }

    @Override
    public MessageDTO getOne(String personId, String messageId) {
        log.info("Received a request to get message by id: : {}, that belongs to person with id: {}", messageId, personId);

        // get one message
        Message message = messageService.getOne(messageId, personId);

        // map back to the DTO and return
        return messageDTOMapper.toDTO(message, personId);
    }

    @Override
    public List<MessageDTO> getAll(String personId) {
        log.info("Received a request to get all messages of person with id: {}", personId);

        // get all messages of a person
        List<Message> messages = messageService.getAll(personId);

        return messageDTOMapper.toMessagesDTOs(messages);
    }

    @Override
    public MessageDTO updateMessage(@RequestBody @ParameterObject MessageDTO messageDTO, @PathVariable String personId, @PathVariable String messageId) {
        log.info("Received request to update message with id: {}, for person with id: {}", messageId, personId);

        // map to the domain model
        Message message = messageDTOMapper.toModel(messageDTO);

        // update the message
        Message updatedMessage = messageService.updateMessage(message, personId, messageId);

        log.info("Message with id {} was updated", updatedMessage.getId());

        // map back to the DTO and return
        return messageDTOMapper.toDTO(updatedMessage, personId);
    }

    @Override
    public void deleteMessage(@PathVariable String personId, @PathVariable String messageId) {
        log.info("Received request to delete message with id: {}, for person with id: {}", messageId, personId);

        // delete the message
        messageService.deleteMessage(personId, messageId);

        log.info("Message with id {} was deleted", messageId);
    }
}
