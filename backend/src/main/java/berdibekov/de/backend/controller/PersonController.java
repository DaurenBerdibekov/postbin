package berdibekov.de.backend.controller;

import berdibekov.de.backend.dto.PersonDTO;
import org.hibernate.validator.constraints.UUID;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = PersonController.REST_URL)
public interface PersonController {

    String REST_URL = "/api/v1/persons";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PersonDTO createPerson(@RequestBody @ParameterObject PersonDTO personDTO);

    @DeleteMapping("/{personId}")
    @ResponseStatus(HttpStatus.OK)
    void deletePerson(@PathVariable @UUID String personId);
}
