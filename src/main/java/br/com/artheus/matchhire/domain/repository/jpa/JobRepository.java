package br.com.artheus.matchhire.domain.repository;

import br.com.artheus.matchhire.domain.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {

    /**
     * Finds a job by its public UUID.
     */
    Optional<Job> findByPublicId(UUID publicId);

    /**
     * Find all active jobs
     */
    List<Job> findByActiveTrue();
}
