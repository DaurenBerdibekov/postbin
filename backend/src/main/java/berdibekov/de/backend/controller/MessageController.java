package berdibekov.de.backend.controller;

import berdibekov.de.backend.dto.ErrorGroupDTO;
import berdibekov.de.backend.dto.MessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = MessageController.REST_URL)
public interface MessageController {

    String REST_URL = "/api/v1/persons/{personId}/messages";

    @Operation(
            summary = "Creates a new message",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Returns the newly created product with new id assigned.",
                            content = @Content(schema = @Schema(implementation = MessageDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "Invalid Request Body (Some validation failed)",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "415", description = "Media Type Not Supported",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    MessageDTO createMessage(@PathVariable String personId, @RequestBody @ParameterObject MessageDTO messageDTO);

    @Operation(
            summary = "Get one message of person",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Invalid Request Body (Some validation failed)",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "Message not found",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @GetMapping(value = "/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    MessageDTO getOne(@PathVariable String personId, @PathVariable String messageId);

    @Operation(
            summary = "Get all message of person",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Invalid Request Body (Some validation failed)",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "Message not found",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    List<MessageDTO> getAll(@PathVariable String personId);

    @Operation(
            summary = "Updates an existing message",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns DTO of the updated message.",
                            content = @Content(schema = @Schema(implementation = MessageDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "Invalid Request Body (Some validation failed)",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "Message or person not found",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @PutMapping(value = "/{messageId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    MessageDTO updateMessage(MessageDTO messageDTO, @PathVariable String personId, @PathVariable String messageId);

    @Operation(
            summary = "Deletes an existing message",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Message successfully deleted."),
                    @ApiResponse(responseCode = "404", description = "Message or person not found",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorGroupDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    @DeleteMapping(value = "/{messageId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteMessage(@PathVariable String personId, @PathVariable String messageId);

    @GetMapping(value = "/friend/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    List<MessageDTO> getMessagesOfPersonsFriend(@PathVariable String personId, @PathVariable String friendId);
}
