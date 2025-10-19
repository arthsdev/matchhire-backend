package br.com.artheus.matchhire.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Represents a candidate applying for jobs.
 */
@Entity(name = "candidates")
@Table(name = "candidates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID publicId;

    @NotBlank
    @Size(min = 10, max = 100)
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    private String skills;

    @Min(0)
    @Max(50)
    private Integer experience;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    @Builder.Default
    private boolean active = true;

    @PrePersist
    public void prePersist() {
        if (publicId == null) publicId = UUID.randomUUID();
    }
}
