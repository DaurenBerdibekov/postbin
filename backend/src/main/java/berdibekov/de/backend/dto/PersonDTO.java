package berdibekov.de.backend.dto;

public record PersonDTO(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
