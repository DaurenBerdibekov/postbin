package berdibekov.de.backend.dto;

import java.util.List;

@SuppressWarnings("unused")
public record ErrorGroupDTO(
        List<ErrorDTO> errors
) {
}
