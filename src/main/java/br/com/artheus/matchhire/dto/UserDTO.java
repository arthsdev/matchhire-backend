package br.com.artheus.matchhire.dto;

import br.com.artheus.matchhire.domain.model.enums.UserRole;

/**
 * DTO for exposing user data safely via API.
 * Only exposes the publicId, login, and role.
 */
public record UserDTO(String publicId, String login, UserRole role) {
}
