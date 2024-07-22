package berdibekov.de.backend.repository;

import berdibekov.de.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    Optional<Message> findByIdAndPersonId(String messageId, String personId);

    List<Message> findAllByPersonId(String personId);

}