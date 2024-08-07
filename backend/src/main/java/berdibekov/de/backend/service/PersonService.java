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
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private static final String NOT_FOUND_PERSON = "Message with id '%s' does not exist";
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
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PERSON.formatted(personId)));

        personRepository.delete(existingPerson);
    }

    @Transactional
    public Person updatePerson(Person person, String personId) {
        log.info("Updating person with id: {}", personId);

        Person existingPerson = personRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PERSON.formatted(personId)));

        existingPerson.setFirstname(person.getFirstname());
        existingPerson.setLastname(person.getLastname());
        existingPerson.setEmail(person.getEmail());
        existingPerson.setCreatedAt(LocalDateTime.now());

        return personRepository.save(existingPerson);
    }

    public Person getOne(String personId) {
        return personRepository.findById(personId).orElseThrow(() -> new NotFoundException(NOT_FOUND_PERSON.formatted(personId)));
    }

    public Person findByEmailAndPassword(String email, String password) {
        return personRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new NotFoundException(NOT_FOUND_PERSON));
    }


    @Transactional
    public void addFriendToPerson(String personId, String friendPersonId) {

        log.info("Adding a friend to person with id: {}", personId);

        Person existingPerson = personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found"));
        Person friend = personRepository.findById(friendPersonId).orElseThrow(() -> new NotFoundException("Friend not found"));

        // check if friend already exists
        if (existingPerson.getFriends().contains(friend)) {
            log.info("Friend with id: {} is already a friend of person with id: {}", friendPersonId, personId);
        }

        // add friend to person
        existingPerson.getFriends().add(friend);

        personRepository.save(existingPerson);
    }

    @Transactional(readOnly = true)
    public Set<Person> getAllFriendsOfPerson(String personId) {
        Set<Person> person = personRepository.findFriendsByPersonId(personId);

        if(person.isEmpty()){
            log.info("Person with id: {} don't have any friends", personId);
        }

        return person;
    }

    public Person findPersonByFirstnameAndLastname(String firstname, String lastname) {

        return personRepository.findByFirstnameContainingIgnoreCaseAndLastnameContainingIgnoreCase(firstname, lastname)
                .orElseThrow(() -> new NotFoundException(String.format("No persons found with firstname containing '%s' and lastname containing '%s'", firstname, lastname)));

    }
}
