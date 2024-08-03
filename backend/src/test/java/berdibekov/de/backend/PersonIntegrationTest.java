package berdibekov.de.backend;
import berdibekov.de.backend.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BackendLocal.class)
@ActiveProfiles("local")
@ContextConfiguration(initializers = AllContainerInitializer.class)
class PersonIntegrationTest {

    private static final String API_V1_PERSON = "/api/v1/persons";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void createPerson_shouldCreatePerson() {
        PersonDTO expectedPerson = new PersonDTO(
                null,
                "John",
                "Doe",
                "john.doe@example.com",
                "Qwerty_01");


        ResponseEntity<PersonDTO> response = testRestTemplate.postForEntity(API_V1_PERSON, expectedPerson, PersonDTO.class);
        PersonDTO actualPerson = response.getBody();


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(actualPerson);
        assertEquals(expectedPerson.firstname(), actualPerson.firstname());
        assertEquals(expectedPerson.lastname(), actualPerson.lastname());
        assertEquals(expectedPerson.email(), actualPerson.email());
        assertEquals(expectedPerson.password(), actualPerson.password());

    }

    @Test
    void getPerson_shouldReturnPerson() {
        PersonDTO expectedPerson = new PersonDTO(
                null,
                "John",
                "Doe",
                "john.doe@example.com",
                "Qwerty_01");

        ResponseEntity<PersonDTO> createResponse = testRestTemplate.postForEntity(API_V1_PERSON, expectedPerson, PersonDTO.class);
        String personId = Objects.requireNonNull(createResponse.getBody()).id();

        ResponseEntity<PersonDTO> response = testRestTemplate.getForEntity(API_V1_PERSON + "/" + personId, PersonDTO.class);
        PersonDTO actualPerson = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(actualPerson);
        assertEquals(expectedPerson.firstname(), actualPerson.firstname());
        assertEquals(expectedPerson.lastname(), actualPerson.lastname());
        assertEquals(expectedPerson.email(), actualPerson.email());
        assertEquals(expectedPerson.password(), actualPerson.password());

    }

    @Test
    void updatePerson_shouldUpdatePerson() {
        PersonDTO person = new PersonDTO(
                "",
                "John",
                "Doe",
                "john.doe@example.com",
                "Qwerty_01");

        ResponseEntity<PersonDTO> createResponse = testRestTemplate.postForEntity(API_V1_PERSON, person, PersonDTO.class);
        String personId = Objects.requireNonNull(createResponse.getBody()).id();

        PersonDTO expectedPerson = new PersonDTO(
                personId,
                "Max",
                "Mustermann",
                "max.mustermann@example.de",
                "Qwerty_02");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PersonDTO> entity = new HttpEntity<>(expectedPerson, headers);

        ResponseEntity<PersonDTO> response = testRestTemplate.exchange(API_V1_PERSON + "/" + personId, HttpMethod.PUT, entity, PersonDTO.class);
        PersonDTO actualPerson = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(actualPerson);
        assertEquals(expectedPerson.firstname(), actualPerson.firstname());
        assertEquals(expectedPerson.lastname(), actualPerson.lastname());
        assertEquals(expectedPerson.email(), actualPerson.email());

        // TODO implement password change
        //assertEquals(expectedPerson.password(), actualPerson.password());
    }

    @Test
    void deletePerson_shouldDeletePerson() {
        PersonDTO person = new PersonDTO(
                "",
                "John",
                "Doe",
                "john.doe@example.com",
                "Qwerty_01");

        // create person
        ResponseEntity<PersonDTO> createResponse = testRestTemplate.postForEntity(API_V1_PERSON, person, PersonDTO.class);
        String personId = Objects.requireNonNull(createResponse.getBody()).id();

        ResponseEntity<Void> response = testRestTemplate.exchange(API_V1_PERSON + "/" + personId, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
