package berdibekov.de.backend.service;

import berdibekov.de.backend.exception.NotFoundException;
import berdibekov.de.backend.model.Message;
import berdibekov.de.backend.model.Person;
import berdibekov.de.backend.repository.MessageRepository;
import berdibekov.de.backend.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private static final String NOT_FOUND_MESSAGE = "Message with id '%s' does not exist";
    private final Util util;
    private final MessageRepository messageRepository;

    public Message createMessage(Message message, String personId){

        message.setCreatedAt(util.getLocalDateTime());
        message.setId(util.generateRandomUUID());

        Person person = new Person();
        person.setId(personId);
        message.setPerson(person);

        return messageRepository.save(message);
    }

    public Message getOne(String messageId, String personId) {

        return messageRepository.findByIdAndPersonId(messageId, personId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(messageId)));

    }

    public List<Message> getAll(String personId) {
        List<Message> messages = messageRepository.findAllByPersonId(personId);

        if (messages.isEmpty()){
            log.info("Person with id: {} does not have any messages", personId);
            throw new IllegalArgumentException();
        }

        return messages;
    }

    public Message updateMessage(Message message, String personId, String messageId) {
        log.info("Updating message with id: {} for person with id: {}", messageId, personId);

        Message existingMessage = messageRepository.findByIdAndPersonId(messageId, personId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(messageId)));

        existingMessage.setContent(message.getContent());  // Обновите нужные поля
        existingMessage.setCreatedAt(util.getLocalDateTime());

        return messageRepository.save(existingMessage);
    }

    public void deleteMessage(String personId, String messageId) {
        log.info("Deleting message with id: {} for person with id: {}", messageId, personId);

        Message existingMessage = messageRepository.findByIdAndPersonId(messageId, personId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(messageId)));

        messageRepository.delete(existingMessage);
    }
}
