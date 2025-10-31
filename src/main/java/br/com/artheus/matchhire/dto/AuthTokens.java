package br.com.artheus.matchhire.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO representing access and refresh tokens.
 * refreshToken is stored only in HttpOnly cookie.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // prevents null fields from being serialized
public record AuthTokens(String accessToken, String refreshToken) {

    /**
     * Constructor for responses where refreshToken should not be returned in body.
     */
    public AuthTokens(String accessToken) {
        this(accessToken, null);
    }
}
