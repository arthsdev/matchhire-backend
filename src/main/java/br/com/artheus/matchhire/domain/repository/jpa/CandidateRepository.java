package br.com.artheus.matchhire.domain.repository;

import br.com.artheus.matchhire.domain.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    Optional<Candidate> findByPublicId(UUID publicId);

    Optional<Candidate> findByEmail(String email);

    List<Candidate> findByActiveTrue();
}
