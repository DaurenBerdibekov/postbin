package berdibekov.de.backend.junit;
import berdibekov.de.backend.exception.NotFoundException;
import berdibekov.de.backend.model.Message;
import berdibekov.de.backend.repository.MessageRepository;
import berdibekov.de.backend.service.MessageService;
import berdibekov.de.backend.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    private final String NOT_FOUND_MESSAGE = "Message with id '%s' does not exist";

    @Mock
    private Util util;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    private Message message;
    private final String personId = String.valueOf(UUID.randomUUID());
    private final String messageId = String.valueOf(UUID.randomUUID());

    @BeforeEach
    void setUp() {
        // given
        message = new Message();
        message.setId(messageId);
        message.setSubject("Test Subject");
        message.setContent("Test Content");
    }

    @Test
    void createMessage_shouldCreateMessage() {
        // given
        LocalDateTime now = LocalDateTime.now();

        // when
        when(util.generateRandomUUID()).thenReturn(messageId);
        when(util.getLocalDateTime()).thenReturn(now);
        when(messageRepository.save(message)).thenReturn(message);

        Message createdMessage = messageService.createMessage(personId, message);

        // then
        assertNotNull(createdMessage);
        assertEquals(messageId, createdMessage.getId());
        assertEquals(now, createdMessage.getCreatedAt());
        assertEquals(personId, createdMessage.getPerson().getId());
        verify(messageRepository).save(message);
        verify(util).generateRandomUUID();
        verify(util).getLocalDateTime();
    }

    @Test
    void getOneMessage_shouldReturnOneMessage_whenItExists() {
        // when
        when(messageRepository.findByIdAndPersonId(messageId, personId)).thenReturn(Optional.of(message));

        Message foundMessage = messageService.getOne(messageId, personId);

        assertNotNull(foundMessage);
        assertEquals(messageId, foundMessage.getId());
        verify(messageRepository).findByIdAndPersonId(messageId, personId);
    }

    @Test
    void getOne_shouldReturnMessageNotFoundException_whenMessageDoesNotExist() {
        // when
        when(messageRepository.findByIdAndPersonId(messageId, personId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> messageService.getOne(messageId, personId));

        String expectedMessage = String.format(NOT_FOUND_MESSAGE, messageId);
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getAllMessages_shouldReturnAllMessagesOfPerson() {
        // given
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        // when
        when(messageRepository.findAllByPersonId(personId)).thenReturn(messages);

        List<Message> foundMessages = messageService.getAll(personId);

        // then
        assertNotNull(foundMessages);
        assertFalse(foundMessages.isEmpty());
        verify(messageRepository).findAllByPersonId(personId);
    }

    @Test
    void updateMessage_shouldUpdateMessage() {
        // given
        LocalDateTime now = LocalDateTime.now();

        // when
        when(messageRepository.findByIdAndPersonId(messageId, personId)).thenReturn(Optional.of(message));
        when(util.getLocalDateTime()).thenReturn(now);

        Message updatedMessage = new Message();
        updatedMessage.setSubject("Updated Subject");
        updatedMessage.setContent("Updated Content");
        updatedMessage.setCreatedAt(now);

        when(messageRepository.save(message)).thenReturn(updatedMessage);

        Message result = messageService.updateMessage(updatedMessage, personId, messageId);

        // then
        assertNotNull(result);
        assertEquals("Updated Subject", result.getSubject());
        assertEquals("Updated Content", result.getContent());
        assertEquals(now, result.getCreatedAt());
        verify(messageRepository).save(message);
        verify(messageRepository).findByIdAndPersonId(messageId, personId);
    }

    @Test
    void updateMessage_shouldReturnNotFoundException_whenMessageDoesNotExist() {
        // when
        when(messageRepository.findByIdAndPersonId(messageId, personId)).thenReturn(Optional.empty());

        Message updatedMessage = new Message();
        updatedMessage.setSubject("Updated Subject");
        updatedMessage.setContent("Updated Content");

        Exception exception = assertThrows(NotFoundException.class, () -> messageService.updateMessage(updatedMessage, personId, messageId));

        String expectedMessage = String.format(NOT_FOUND_MESSAGE, messageId);
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteMessage_shouldDeleteMessage() {
        // when
        when(messageRepository.findByIdAndPersonId(messageId, personId)).thenReturn(Optional.of(message));

        messageService.deleteMessage(personId, messageId);

        // then
        verify(messageRepository).delete(message);
        verify(messageRepository, times(1)).findByIdAndPersonId(messageId, personId);
    }

    @Test
    void deleteMessage_shouldReturnNotFoundException_whenMessageDoesNotExist() {
        // when
        when(messageRepository.findByIdAndPersonId(messageId, personId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> messageService.deleteMessage(personId, messageId));

        String expectedMessage = String.format(NOT_FOUND_MESSAGE, messageId);
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
