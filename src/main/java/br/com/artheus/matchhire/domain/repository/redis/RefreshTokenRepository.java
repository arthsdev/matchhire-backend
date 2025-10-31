package br.com.artheus.matchhire.domain.repository.redis;

import br.com.artheus.matchhire.domain.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing refresh tokens in Redis.
 * Provides methods to find, save, and delete tokens by username or ID.
 */
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, UUID> {

    /**
     * Find a refresh token by username.
     *
     * @param username the username associated with the token
     * @return an Optional containing the RefreshToken if found
     */
    Optional<RefreshToken> findByUsername(String username);

    /**
     * Find a refresh token by token value.
     *
     * @param token the refresh token string
     * @return an Optional containing the RefreshToken if found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Delete a refresh token by username.
     *
     * @param username the username associated with the token
     */
    void deleteByUsername(String username);
}
