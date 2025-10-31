package br.com.artheus.matchhire.service;

import br.com.artheus.matchhire.domain.model.User;
import br.com.artheus.matchhire.domain.repository.jpa.UserRepository;
import br.com.artheus.matchhire.infrastructure.security.TokenService;
import br.com.artheus.matchhire.infrastructure.util.CookieHelper;
import br.com.artheus.matchhire.dto.AuthResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final RedisService redisService;

    /**
     * Internal record to hold login result (access + refresh token)
     * Only accessToken is returned in DTO; refreshToken goes to cookie.
     */
    public record LoginResult(String accessToken, String refreshToken) {}

    /**
     * Authenticate user and generate tokens
     */
    public LoginResult login(String login, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );

        var user = (User) authentication.getPrincipal();

        var accessToken = tokenService.generateAccessToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        // store refresh token in Redis using userId
        redisService.storeRefreshToken(user.getId(), refreshToken);

        return new LoginResult(accessToken, refreshToken);
    }

    /**
     * Refresh access token using a valid refresh token
     */
    public AuthResponseDTO refreshAccessToken(String refreshToken, HttpServletResponse response) {
        var username = tokenService.extractUsername(refreshToken);
        var user = userRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UUID userId = user.getId();

        // validate token stored in Redis
        redisService.validateStoredRefreshToken(userId, refreshToken);

        var newAccessToken = tokenService.generateAccessToken(user);
        var newRefreshToken = tokenService.generateRefreshToken(user);

        // update Redis with new refresh token
        redisService.storeRefreshToken(userId, newRefreshToken);

        // attach cookie (refreshToken only in cookie)
        response.addHeader("Set-Cookie", CookieHelper.buildRefreshTokenCookie(newRefreshToken).toString());

        return new AuthResponseDTO(newAccessToken);
    }
}
