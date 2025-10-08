package br.com.artheus.matchhire.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Represents a company posting job vacancies.
 * A company can have multiple jobs posted.
 * Includes an active flag for soft deletion.
 */
@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @NotBlank(message = "Company name cannot be blank")
    private String name;

    private String industry;

    private String description;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Job> jobs;

    private boolean active = true;

    /**
     * Lifecycle callback executed before persisting.
     * Ensures that a publicId is generated if missing.
     */
    @PrePersist
    public void prePersist() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}
