package br.com.artheus.matchhire.infrastructure.util;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

/**
 * Utility class for creating secure HTTP-only cookies for refresh tokens.
 */
public class CookieHelper {

    /**
     * Builds a secure HTTP-only cookie with refresh token.
     * Can be reused for login and refresh endpoints.
     */
    public static ResponseCookie buildRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)       // enable HTTPS in production
                .path("/")
                .maxAge(Duration.ofDays(7)) // match refresh token TTL
                .sameSite("Strict") // prevent CSRF
                .build();
    }

    /**
     * Builds a cookie to clear the refresh token.
     * Used in logout endpoint.
     */
    public static ResponseCookie buildClearRefreshTokenCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }
}
