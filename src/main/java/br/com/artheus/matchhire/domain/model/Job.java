package br.com.artheus.matchhire.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Represents a job vacancy posted by a company.
 */
@Entity(name = "jobs")
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID publicId;

    @NotBlank
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

    @Builder.Default
    private boolean active = true;

    @PrePersist
    public void prePersist() {
        if (publicId == null) publicId = UUID.randomUUID();
    }
}
