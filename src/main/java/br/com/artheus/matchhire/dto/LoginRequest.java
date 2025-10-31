package br.com.artheus.matchhire.dto;


import jakarta.validation.constraints.NotBlank;

/**
 * DTO for login request.
 */
public record LoginRequest(
        @NotBlank String login,
        @NotBlank String password
) { }
