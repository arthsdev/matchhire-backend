package br.com.artheus.matchhire.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpirationMillis;

    public RefreshTokenService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Gera e armazena um novo refresh token associado ao usuário.
     * Expira automaticamente após o tempo configurado.
     */
    public String generateRefreshToken(String username) {
        String refreshToken = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(
                getKey(refreshToken),
                username,
                Duration.ofMillis(refreshTokenExpirationMillis)
        );
        return refreshToken;
    }

    /**
     * Valida se o refresh token existe e ainda não expirou.
     */
    public boolean validateRefreshToken(String refreshToken) {
        return redisTemplate.opsForValue().get(getKey(refreshToken)) != null;
    }

    /**
     * Retorna o username associado ao token, se existir.
     */
    public String getUsernameFromRefreshToken(String refreshToken) {
        return redisTemplate.opsForValue().get(getKey(refreshToken));
    }

    /**
     * Revoga (deleta) o token, usado no logout.
     */
    public void revokeRefreshToken(String refreshToken) {
        redisTemplate.delete(getKey(refreshToken));
    }

    private String getKey(String refreshToken) {
        return "refresh_token:" + refreshToken;
    }
}
