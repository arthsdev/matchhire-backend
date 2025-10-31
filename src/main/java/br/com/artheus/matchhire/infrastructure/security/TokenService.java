package br.com.artheus.matchhire.infrastructure.security;

import br.com.artheus.matchhire.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service responsible for generating and validating JWT tokens
 */
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 min
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 days

    public String generateAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("type", "refresh")
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(secret));
    }

    public String extractUsername(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getSubject();
    }

    public void attachRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (REFRESH_TOKEN_EXPIRATION / 1000));
        response.addCookie(cookie);
    }
}
