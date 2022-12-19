package org.maciejklonicki.passwordapigenerator.password;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@NoArgsConstructor
@ToString
public class Password {

    @Id
    @SequenceGenerator(
            name = "password_id_sequence",
            sequenceName = "password_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "password_id_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private Instant dateOfPasswordCreation;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String complexity;

    public Password(Long id, String password, String complexity) {
        this.id = id;
        this.dateOfPasswordCreation = Instant.now();
        this.password = password;
        this.complexity = complexity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateOfPasswordCreation() {
        return dateOfPasswordCreation;
    }

    public void setDateOfPasswordCreation(Instant dateOfPasswordCreation) {
        this.dateOfPasswordCreation = dateOfPasswordCreation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(id, password1.id) && Objects.equals(dateOfPasswordCreation, password1.dateOfPasswordCreation) && Objects.equals(password, password1.password) && Objects.equals(complexity, password1.complexity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfPasswordCreation, password, complexity);
    }
}
