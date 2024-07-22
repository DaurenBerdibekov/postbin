package berdibekov.de.backend.service;

import berdibekov.de.backend.exception.NotFoundException;
import berdibekov.de.backend.model.Person;
import berdibekov.de.backend.repository.PersonRepository;
import berdibekov.de.backend.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {
    private final Util util;
    private final PersonRepository personRepository;

    @Transactional
    public Person createPerson(Person person) {
        person.setId(util.generateRandomUUID());
        person.setCreatedAt(util.getLocalDateTime());

        return personRepository.save(person);
    }

    public void deletePerson(String personId){
        log.info("Delete person with id: {}", personId);

        Person existingPerson = personRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException("Person with id '%s' does not exist.".formatted(personId)));

        personRepository.delete(existingPerson);
    }

    @Transactional
    public Person updatePerson(Person person, String personId) {
        log.info("Updating person with id: {}", personId);

        Person existingPerson = personRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException("Person with id '%s' does not exist.".formatted(personId)));

        existingPerson.setFirstname(person.getFirstname());
        existingPerson.setLastname(person.getLastname());
        existingPerson.setEmail(person.getEmail());
        existingPerson.setCreatedAt(LocalDateTime.now());

        return personRepository.save(existingPerson);
    }
}
