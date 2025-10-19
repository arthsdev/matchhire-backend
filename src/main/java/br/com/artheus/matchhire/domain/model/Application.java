package br.com.artheus.matchhire.domain.model;

import br.com.artheus.matchhire.domain.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * Represents a candidate's application to a specific job.
 */
@Entity(name = "applications")
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false, columnDefinition = "BINARY(16)")
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
    @Builder.Default
    private Status status = Status.PENDING;

    private Double score;

    @Builder.Default
    private boolean active = true;

    @PrePersist
    public void prePersist() {
        if (publicId == null) publicId = UUID.randomUUID();
    }
}
