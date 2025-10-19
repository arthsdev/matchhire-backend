package br.com.artheus.matchhire.service;

import br.com.artheus.matchhire.domain.model.User;
import br.com.artheus.matchhire.infrastructure.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Service responsible for authenticating users and generating JWT tokens
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * Authenticate user with username/password and generate a JWT token
     */
    public String login(String login, String password) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );

        var user = (User) auth.getPrincipal();

        return tokenService.generateToken(user);
    }
}
