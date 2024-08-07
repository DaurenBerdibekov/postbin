package berdibekov.de.backend.repository;

import berdibekov.de.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findById(String personId);

    Optional<Person> findByEmailAndPassword(String email, String password);

    Optional<Person> findByFirstnameContainingIgnoreCaseAndLastnameContainingIgnoreCase(String firstname, String lastname);

    @Query(value = "SELECT p.* FROM person p " +
            "JOIN person_friends pf ON p.id = pf.friends_id " +
            "WHERE pf.person_id = :personId", nativeQuery = true)
    Set<Person> findFriendsByPersonId(@Param("personId") String personId);
}
