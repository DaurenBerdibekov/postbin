package berdibekov.de.backend.controller;

import berdibekov.de.backend.dto.ErrorGroupDTO;
import berdibekov.de.backend.dto.MessageDTO;
import berdibekov.de.backend.dto.PersonDTO;
import berdibekov.de.backend.dto.LoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = PersonController.REST_URL)
public interface PersonController {

    String REST_URL = "/api/v1/persons";

    @Operation(
            summary = "Creates a new person",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Returns the newly created person with new id assigned.",
                            content = @Content(schema = @Schema(implementation = MessageDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "Invalid Request Body (Some validation failed)",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "415", description = "Media Type Not Supported",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PersonDTO createPerson(@RequestBody @ParameterObject PersonDTO personDTO);

    @Operation(
            summary = "Get one person",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Returns the newly created person with new id assigned.",
                            content = @Content(schema = @Schema(implementation = MessageDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "Invalid Request Body (Some validation failed)",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @GetMapping(value = "/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    PersonDTO getPerson(@PathVariable String personId);

    @Operation(
            summary = "Update person",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns DTO of the updated person.",
                            content = @Content(schema = @Schema(implementation = MessageDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "Invalid Request Body (Some validation failed)",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "Person not found",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @PutMapping(value = "/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    PersonDTO updatePerson(@RequestBody PersonDTO personDTO, @PathVariable String personId);


    @Operation(
            summary = "Delete person",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Person successfully deleted."),
                    @ApiResponse(responseCode = "404", description = "Person not found",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @DeleteMapping("/{personId}")
    @ResponseStatus(HttpStatus.OK)
    void deletePerson(@PathVariable String personId);

    @Operation(
            summary = "Authenticate person by email and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns the authenticated person.",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "401", description = "Invalid email or password",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PersonDTO authenticatePerson(@RequestBody @ParameterObject LoginRequestDTO loginRequestDTO);

}
