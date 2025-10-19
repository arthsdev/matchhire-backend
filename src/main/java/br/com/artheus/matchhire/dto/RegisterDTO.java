package br.com.artheus.matchhire.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank String login,
        @NotBlank String password
) {}