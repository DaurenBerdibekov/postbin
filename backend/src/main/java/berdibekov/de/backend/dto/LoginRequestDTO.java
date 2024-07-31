package berdibekov.de.backend.dto;

public record LoginRequestDTO(
        String email,
        String password
) {
}
