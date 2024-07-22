package berdibekov.de.backend.dto;

public record MessageDTO(
        String personId,
        String subject,
        String content
) {
}
