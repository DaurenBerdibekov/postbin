package berdibekov.de.backend.service;

import berdibekov.de.backend.model.Person;
import berdibekov.de.backend.repository.PersonRepository;
import berdibekov.de.backend.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {
    private final Util util;
    private final PersonRepository personRepository;

    public Person createPerson(Person person) {
        person.setId(util.generateRandomUUID());
        person.setCreatedAt(util.getLocalDateTime());

        return personRepository.save(person);
    }

    public void deletePerson(String personId){
        personRepository.deleteById(UUID.fromString(personId));
    }
}
