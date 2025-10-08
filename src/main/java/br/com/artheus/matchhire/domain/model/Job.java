package br.com.artheus.matchhire.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Represents a job vacancy posted by a company.
 * Each job belongs to a company and can have multiple applications.
 * Includes an active flag for soft deletion.
 */
@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @NotBlank(message = "Job title cannot be blank")
    @Size(min = 10, max = 100)
    private String title;

    @NotBlank
    @Size(min = 100, max = 2000)
    private String description;

    private String requirements;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Application> applications;

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
