package berdibekov.de.backend.dto;

public record PersonDTO(
        String id,
        String firstname,
        String lastname,
        String email,
        String password
) {
}
