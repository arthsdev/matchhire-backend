package br.com.artheus.matchhire.infrastructure.security;

import br.com.artheus.matchhire.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service responsible for generating and validating JWT tokens.
 */
@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration; // milliseconds

    /**
     * Generate JWT token for given username
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return JWT.create()
                .withSubject(user.getLogin())
                .withClaim("publicId", user.getPublicId().toString())
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * Validate JWT token and return username
     */
    public String validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException ex) {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    /**
     * Extracts the JWT token from the Authorization header.
     * Example: "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI..."
     */
    public String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.replace("Bearer ", "");
    }
}
