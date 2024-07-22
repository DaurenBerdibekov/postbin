package berdibekov.de.backend.repository;

import berdibekov.de.backend.model.Message;
import berdibekov.de.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findById(String personId);
}
