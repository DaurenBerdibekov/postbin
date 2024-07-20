package berdibekov.de.backend.util;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class Util {
    public String generateRandomUUID(){
        return UUID.randomUUID().toString();
    }

    public LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }
}
