package br.com.artheus.matchhire.exception.authentication;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Invalid or expired refresh token");
    }
}