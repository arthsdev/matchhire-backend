package br.com.artheus.matchhire.controller;

import br.com.artheus.matchhire.dto.AuthRequestDTO;
import br.com.artheus.matchhire.dto.LoginResponseDTO;
import br.com.artheus.matchhire.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller: only login (token) endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthRequestDTO dto) {
        try {
            String token = authService.login(dto.login(), dto.password());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException ex) {
            // Bad credentials -> 401 Unauthorized (standard)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
