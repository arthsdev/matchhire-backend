package br.com.artheus.matchhire.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a refresh token stored in Redis.
 * Implements single-use, revocable, and expirable token logic.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("refresh_tokens") // Redis hash name
public class RefreshToken implements Serializable {

    /**
     * Unique identifier for Redis storage.
     */
    @Id
    private UUID id;

    /**
     * Username associated with this refresh token.
     */
    private String username;

    /**
     * The refresh token string value.
     */
    private String token;

    /**
     * Expiration timestamp for the token.
     */
    private Instant expiryDate;

    /**
     * Flag indicating whether the token has been revoked.
     */
    private boolean revoked;
}
