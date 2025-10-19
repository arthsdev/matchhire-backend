package br.com.artheus.matchhire.controller;

import br.com.artheus.matchhire.domain.model.User;
import br.com.artheus.matchhire.dto.RegisterDTO;
import br.com.artheus.matchhire.dto.RegisterResponseDTO;
import br.com.artheus.matchhire.dto.UserDTO;
import br.com.artheus.matchhire.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller for user operations.
 * Now the register endpoint follows REST conventions (201 Created + Location header).
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Registers a new user and returns their publicId safely.
     * Returns:
     * - 201 Created on success
     * - 409 Conflict if user already exists (handled by UserService)
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterDTO dto) {
        String publicId = userService.register(dto);

        RegisterResponseDTO body = new RegisterResponseDTO(publicId, dto.login(), "USER");
        URI location = URI.create("/api/users/" + publicId);

        return ResponseEntity.created(location).body(body);
    }

    /**
     * Returns all users (ADMIN only).
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Returns information about the currently authenticated user.
     */
    @GetMapping("/me")
    public UserDTO getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        // principal can be a User or username string depending on JWT filter setup
        if (principal instanceof User user) {
            return new UserDTO(user.getPublicId(), user.getLogin(), user.getRole());
        }

        throw new RuntimeException("Invalid authentication principal");
    }

    /**
     * Returns a specific user by publicId (ADMIN only).
     */
    @GetMapping("/{publicId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUser(@PathVariable String publicId) {
        return userService.getUserByPublicId(publicId);
    }
}
