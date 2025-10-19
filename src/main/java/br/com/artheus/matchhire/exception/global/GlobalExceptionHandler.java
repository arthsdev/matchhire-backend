package br.com.artheus.matchhire.exception.global;

import br.com.artheus.matchhire.exception.BadRequestException;
import br.com.artheus.matchhire.exception.BusinessException;
import br.com.artheus.matchhire.exception.NotFoundException;
import br.com.artheus.matchhire.exception.dto.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for the entire application.
 * <p>
 * Converts all thrown exceptions into standardized {@link ApiError} responses.
 * Each error response includes timestamp, HTTP status, message, and a trace ID
 * (when available) for correlation in logs and monitoring systems.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ------------------------------------------------------------------------
    // 401 - Unauthorized (Invalid or missing JWT)
    // ------------------------------------------------------------------------
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthentication(AuthenticationException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", "Invalid or missing authentication token", ex);
    }

    // ------------------------------------------------------------------------
    // 403 - Forbidden (User authenticated but not authorized)
    // ------------------------------------------------------------------------
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Forbidden", "You do not have permission to access this resource", ex);
    }

    // ------------------------------------------------------------------------
    // 404 - Not Found
    // ------------------------------------------------------------------------
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), ex);
    }

    // ------------------------------------------------------------------------
    // 400 - Bad Request
    // ------------------------------------------------------------------------
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), ex);
    }

    // ------------------------------------------------------------------------
    // 400 - Business Rule Violation
    // ------------------------------------------------------------------------
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Business Rule Violation", ex.getMessage(), ex);
    }

    // ------------------------------------------------------------------------
    // 400 - Validation Errors (Bean Validation)
    // ------------------------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String message = String.join("; ", errors);
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", message, ex);
    }

    // ------------------------------------------------------------------------
    // 500 - Internal Server Error
    // ------------------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobal(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), ex);
    }

    // ------------------------------------------------------------------------
    // Helper method to build standardized error response
    // ------------------------------------------------------------------------
    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String error, String message, Exception ex) {
        String traceId = MDC.get("traceId"); // Set by filter/interceptor (if available)

        ApiError body = new ApiError();
        body.setTimestamp(LocalDateTime.now());
        body.setStatus(status.value());
        body.setError(error);
        body.setMessage(message);
        body.setTraceId(traceId);

        // Structured logging for observability and debugging
        log.error("[traceId={}] [{}] {} - {}", traceId, status.value(), error, message, ex);

        return ResponseEntity.status(status).body(body);
    }
}
