package br.com.artheus.matchhire.domain.model;

import br.com.artheus.matchhire.domain.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * Represents a candidate's application to a job.
 * This entity stores information about a candidate applying for a specific job,
 * including application status, optional match score, and active flag for soft deletion.
 */
@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @NotNull
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @NotNull
    private Job job;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private boolean active = true;

    private Double score;

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
