package br.com.artheus.matchhire.controller;

import br.com.artheus.matchhire.dto.AuthRequestDTO;
import br.com.artheus.matchhire.dto.AuthResponseDTO;
import br.com.artheus.matchhire.service.AuthService;
import br.com.artheus.matchhire.infrastructure.util.CookieHelper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request,
                                                 HttpServletResponse response) {

        // Use "login", not "username"
        var loginResult = authService.login(request.login(), request.password());

        // Attach refresh token only in cookie
        response.addHeader("Set-Cookie", CookieHelper.buildRefreshTokenCookie(loginResult.refreshToken()).toString());

        // Return only access token in JSON
        return ResponseEntity.ok(new AuthResponseDTO(loginResult.accessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.refreshAccessToken(refreshToken, response));
    }
}
