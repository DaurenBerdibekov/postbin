package berdibekov.de.backend.dto;

public record MessageDTO(
        String id,
        String personId,
        String subject,
        String content
) {
}
