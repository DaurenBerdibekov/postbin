package berdibekov.de.backend.junit;
import berdibekov.de.backend.exception.NotFoundException;
import berdibekov.de.backend.model.Person;
import berdibekov.de.backend.repository.PersonRepository;
import berdibekov.de.backend.service.PersonService;
import berdibekov.de.backend.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private Util util;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private final String NOT_FOUND_PERSON = "Person with id '%s' does not exist";
    private final String personId = String.valueOf(UUID.randomUUID());
    private Person person;

    @BeforeEach
    void setUp() {
        // given
        person = new Person();
        person.setId(personId);
        person.setFirstname("John");
        person.setLastname("Doe");
        person.setEmail("john.doe@example.com");
        person.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createPerson_shouldCreatePerson() {
        // when
        when(util.generateRandomUUID()).thenReturn(personId);
        when(util.getLocalDateTime()).thenReturn(LocalDateTime.now());
        when(personRepository.save(person)).thenReturn(person);

        Person createdPerson = personService.createPerson(person);

        // then
        assertNotNull(createdPerson);
        assertEquals(personId, createdPerson.getId());
        verify(personRepository).save(person);
    }

    @Test
    void deletePerson_shouldDeletePerson() {
        // when
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        personService.deletePerson(personId);

        // then
        verify(personRepository).delete(person);
        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    void updatePerson_shouldUpdateParson() {
        // when
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);

        person.setLastname("Max");
        person.setFirstname("Mustermann");

        Person updatedPerson = personService.updatePerson(person, personId);

        // then
        assertNotNull(updatedPerson);
        assertEquals(personId, updatedPerson.getId());
        assertEquals(person.getFirstname(), updatedPerson.getFirstname());
        assertEquals(person.getLastname(), updatedPerson.getLastname());
        verify(personRepository).save(person);
        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    void getOne_shouldReturnOnePerson() {
        // when
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        Person foundPerson = personService.getOne(personId);

        // then
        assertNotNull(foundPerson);
        assertEquals(personId, foundPerson.getId());
        verify(personRepository).findById(personId);
    }
}
