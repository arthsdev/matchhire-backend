package br.com.artheus.matchhire.domain.repository;

import br.com.artheus.matchhire.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByName(String name);

    Optional<Company> findByIdAndActiveTrue(UUID id);

    /**
     * Finds a company by its public UUID.
     */
    Optional<Company> findByPublicId(UUID publicId);
}
