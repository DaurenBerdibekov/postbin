package berdibekov.de.backend.controller;

import berdibekov.de.backend.dto.PersonDTO;
import berdibekov.de.backend.dto.LoginRequestDTO;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping(value = PersonController.REST_URL)
public interface PersonController {

    String REST_URL = "/api/v1/persons";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PersonDTO createPerson(@RequestBody @ParameterObject PersonDTO personDTO);

    @GetMapping(value = "/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    PersonDTO getPerson(@PathVariable String personId);

    @GetMapping(value = "/{personId}/allPersons", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    List<PersonDTO> getAllPersons(@PathVariable String personId);

    @GetMapping("/{personId}/friend/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    PersonDTO getFriendOfPerson(@PathVariable String personId, @PathVariable String friendId);

    @PutMapping(value = "/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    PersonDTO updatePerson(@RequestBody PersonDTO personDTO, @PathVariable String personId);

    @DeleteMapping("/{personId}")
    @ResponseStatus(HttpStatus.OK)
    void deletePerson(@PathVariable String personId);

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PersonDTO authenticatePerson(@RequestBody @ParameterObject LoginRequestDTO loginRequestDTO);

    @PutMapping(value = "/{personId}/friend/{friendPersonId}/addFriend")
    @ResponseStatus(HttpStatus.OK)
    void addFriendToPerson(@PathVariable String personId, @PathVariable String friendPersonId);

    @GetMapping(value = "/{personId}/friends")
    @ResponseStatus(HttpStatus.OK)
    Set<PersonDTO> getAllFriendsOfPerson(@PathVariable String personId);

    @GetMapping("/search")
    PersonDTO findFriend(@RequestParam String firstname, @RequestParam String lastname);
}
