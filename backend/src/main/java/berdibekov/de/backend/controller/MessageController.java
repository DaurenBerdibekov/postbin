package berdibekov.de.backend.controller;

import berdibekov.de.backend.dto.MessageDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = MessageController.REST_URL)
public interface MessageController {

    String REST_URL = "/api/v1/persons/{personId}/messages";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    MessageDTO createMessage(@PathVariable String personId, @RequestBody @ParameterObject MessageDTO messageDTO);

    @GetMapping(value = "/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    MessageDTO getOne(@PathVariable String personId, @PathVariable String messageId);

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    List<MessageDTO> getAll(@PathVariable String personId);

    @PutMapping(value = "/{messageId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    MessageDTO updateMessage(MessageDTO messageDTO, @PathVariable String personId, @PathVariable String messageId);

    @DeleteMapping(value = "/{messageId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteMessage(@PathVariable String personId, @PathVariable String messageId);

    @GetMapping(value = "/friend/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    List<MessageDTO> getMessagesOfPersonsFriend(@PathVariable String personId, @PathVariable String friendId);
}
