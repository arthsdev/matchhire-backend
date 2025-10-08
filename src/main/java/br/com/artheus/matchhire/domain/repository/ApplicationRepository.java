package br.com.artheus.matchhire.domain.repository;

import br.com.artheus.matchhire.domain.model.Application;
import br.com.artheus.matchhire.domain.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    /**
     * Finds an application by its public UUID.
     */
    Optional<Application> findByPublicId(UUID publicId);

    /**
     * Finds all active applications for a given candidate.
     */
    List<Application> findByCandidateAndActiveTrue(Candidate candidate);
}
