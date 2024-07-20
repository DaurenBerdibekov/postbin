package berdibekov.de.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
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
}
