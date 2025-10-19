package br.com.artheus.matchhire.domain.repository;

import br.com.artheus.matchhire.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);

    Optional<User> findByPublicId(String publicId);

}

