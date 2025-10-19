package br.com.artheus.matchhire.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Represents a company that posts job vacancies.
 */
@Entity(name = "companies")
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID publicId;

    @NotBlank(message = "Company name cannot be blank")
    private String name;

    private String industry;
    private String description;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Job> jobs;

    @Builder.Default
    private boolean active = true;

    @PrePersist
    public void prePersist() {
        if (publicId == null) publicId = UUID.randomUUID();
    }
}
