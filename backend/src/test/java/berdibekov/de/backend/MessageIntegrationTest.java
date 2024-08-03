package berdibekov.de.backend;
import berdibekov.de.backend.dto.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BackendLocal.class)
@ActiveProfiles("local")
@ContextConfiguration(initializers = AllContainerInitializer.class)
class MessageIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SqlGroup(value = {@Sql(scripts = {"/db/createPerson.sql"})})
    void testCreateMessage_Success() throws IOException {

        MessageDTO expectedMessage = objectMapper.readValue(new File("src/test/resources/json/createMessage.json"), MessageDTO.class);

        ResponseEntity<MessageDTO> response  = testRestTemplate.postForEntity("/api/v1/persons/123e4567-e89b-12d3-a456-426614174000/messages", expectedMessage, MessageDTO.class);
        MessageDTO createdMessage = response.getBody();

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(createdMessage);
        Assertions.assertEquals(expectedMessage.personId(), createdMessage.personId());
        Assertions.assertEquals(expectedMessage.content(), createdMessage.content());
        Assertions.assertEquals(expectedMessage.subject(), createdMessage.subject());
    }

    @Test
    @SqlGroup(value = {@Sql(scripts = {"/db/createMessage.sql"})})
    void testGetOneMessage_Success() throws IOException {

        MessageDTO expectedMessage = objectMapper.readValue(new File("src/test/resources/json/createMessage.json"), MessageDTO.class);


        ResponseEntity<MessageDTO> response = testRestTemplate.getForEntity("/api/v1/persons/123e4567-e89b-12d3-a456-426614174000/messages/543e4567-e89b-12d3-a456-456614171234",  MessageDTO.class);
        MessageDTO actualMessage = response.getBody();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(actualMessage);
        Assertions.assertEquals(expectedMessage.personId(), actualMessage.personId());
        Assertions.assertEquals(expectedMessage.content(), actualMessage.content());
        Assertions.assertEquals(expectedMessage.subject(), actualMessage.subject());
    }
}
