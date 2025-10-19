package br.com.artheus.matchhire.dto;

/**
 * Data Transfer Object (DTO) representing the response returned
 * after a successful user registration.
 * This class is immutable and designed to provide essential user information
 * back to the client (e.g., frontend) without exposing sensitive fields such as
 * passwords or tokens.
 */
public record RegisterResponseDTO(String publicId, String login, String role) {}
