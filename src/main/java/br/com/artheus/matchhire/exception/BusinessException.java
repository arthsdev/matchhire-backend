package br.com.artheus.matchhire.exception;

/**
 * Generic exception for business logic errors.
 * Extends RuntimeException so it can be unchecked.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
