package br.com.artheus.matchhire.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

/**
 * Service to manage refresh tokens in Redis.
 * Each user can have one valid refresh token at a time.
 */
@Service
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    // 7 days TTL
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Store or update a refresh token in Redis for a given userId
     */
    public void storeRefreshToken(UUID userId, String refreshToken) {
        String key = buildKey(userId);
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_TTL);
    }

    /**
     * Validate that the given refresh token matches the stored token
     * @throws RuntimeException if invalid or expired
     */
    public void validateStoredRefreshToken(UUID userId, String refreshToken) {
        String key = buildKey(userId);
        String storedToken = redisTemplate.opsForValue().get(key);

        if (storedToken == null) {
            throw new RuntimeException("Refresh token not found or expired");
        }

        if (!storedToken.equals(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    /**
     * Remove refresh token (e.g., on logout)
     */
    public void removeRefreshToken(UUID userId) {
        redisTemplate.delete(buildKey(userId));
    }

    /**
     * Build Redis key for user
     */
    private String buildKey(UUID userId) {
        return "refresh_token:" + userId.toString();
    }
}
