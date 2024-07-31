package berdibekov.de.backend.controller;

import berdibekov.de.backend.dto.LoginRequestDTO;
import berdibekov.de.backend.dto.PersonDTO;
import berdibekov.de.backend.mapper.PersonDTOMapper;
import berdibekov.de.backend.model.Person;
import berdibekov.de.backend.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PersonControllerImpl implements PersonController{

    private final PersonDTOMapper personDTOMapper;
    private final PersonService personService;

    @Override
    public PersonDTO createPerson(PersonDTO personDTO) {
        log.info("Received a request to create person");

        Person person = personDTOMapper.toModel(personDTO);

        Person createdPerson = personService.createPerson(person);

        log.info("The new person with id {} was created", createdPerson.getId());

        return personDTOMapper.toDTO(createdPerson, person.getId());
    }

    @Override
    public PersonDTO getPerson(String personId) {
        log.info("Received a request to get person by id: : {}", personId);

        // get one person
        Person person = personService.getOne(personId);

        // map back to the DTO and return
        return personDTOMapper.toDTO(person, personId);
    }

    @Override
    public PersonDTO updatePerson(@RequestBody PersonDTO personDTO, String personId) {
        log.info("Received request to update person with id: {}", personId);

        // map to the domain model
        Person person = personDTOMapper.toModel(personDTO);

        // update the person
        Person updatedPerson = personService.updatePerson(person, personId);

        log.info("Person with id {} was updated", updatedPerson.getId());

        // map back to the DTO and return
        return personDTOMapper.toDTO(updatedPerson, personId);
    }

    @Override
    public void deletePerson(String personId) {
        log.info("Received a request to delete a person with id: {}", personId);

        personService.deletePerson(personId);

        log.info("The person with id was deleted: {}", personId);
    }

    @Override
    public PersonDTO authenticatePerson(LoginRequestDTO loginRequestDTO) {
        log.info("Received request to authenticate person");

        Person person = personService.findByEmailAndPassword(loginRequestDTO.email(), loginRequestDTO.password());

        return personDTOMapper.toDTO(person, person.getId());
    }
}
