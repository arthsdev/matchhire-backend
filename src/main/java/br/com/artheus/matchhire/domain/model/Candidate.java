package br.com.artheus.matchhire.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Represents a candidate applying for jobs.
 * This entity is part of the core domain model.
 * It stores candidate personal and professional information,
 * and maintains a relationship with job applications.
 */
@Entity
@Table(name = "candidates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @NotBlank(message = "Candidate name cannot be blank")
    @Size(min = 10, max = 100)
    private String name;

    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    private String skills;

    @Min(0)
    @Max(50)
    private Integer experience;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    private boolean active = true;

    /**
     * Lifecycle callback executed before the entity is persisted.
     * Ensures that a publicId is generated if missing.
     */
    @PrePersist
    public void prePersist() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}
