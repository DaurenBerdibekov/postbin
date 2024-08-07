package berdibekov.de.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "person")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "person_friends",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "friends_id")
    )
    private Set<Person> friends = new HashSet<>();

    @ManyToMany(mappedBy = "friends", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Person> friendsOf = new HashSet<>();
}
