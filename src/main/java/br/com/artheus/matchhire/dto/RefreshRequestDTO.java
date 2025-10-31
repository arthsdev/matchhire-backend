package br.com.artheus.matchhire.dto;


import jakarta.validation.constraints.NotBlank;

/**
 * DTO for refresh token request.
 */
public record RefreshRequestDTO(
        @NotBlank String username,
        @NotBlank String refreshToken
) { }
