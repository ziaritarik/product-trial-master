package com.althen.Ecommerce.web.api._resolvers;

import com.althen.Ecommerce.enums.ApiResponseStatus;
import com.althen.Ecommerce.exceptions.JwtAuthenticationException;
import com.althen.Ecommerce.exceptions.ResourceNotFoundException;
import com.althen.Ecommerce.web.dtos.base.ApiResponse;
import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@AllArgsConstructor
public class DefaultExceptionResponseResolver {

    private final MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(final ResourceNotFoundException ex, final WebRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ko(ApiResponseStatus.NOT_FOUND, messageSource.getMessage(ex.getResourceName(), new Object[]{ex.getFieldValue()}, request.getLocale()))
        );
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolationException(final DataIntegrityViolationException ex, final WebRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ko(ApiResponseStatus.VALIDATION, messageSource.getMessage(ex.getMessage(), new Object[]{}, request.getLocale()))
        );
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(final BadCredentialsException ex, final WebRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ko(ApiResponseStatus.RESTRICTED, messageSource.getMessage(ex.getMessage(), new Object[]{}, request.getLocale()))
        );
    }
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleJwtAuthenticationException(final JwtAuthenticationException ex, final WebRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ko(ApiResponseStatus.RESTRICTED, messageSource.getMessage(ex.getMessage(), new Object[]{}, request.getLocale()))
        );
    }
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ApiResponse<Object>> handleServletException(final ServletException ex, final WebRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ko(ApiResponseStatus.SYSTEM,  messageSource.getMessage(ex.getMessage(), new Object[]{}, request.getLocale()))
        );
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(final RuntimeException ex, final WebRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ko(ApiResponseStatus.SYSTEM,  messageSource.getMessage(ex.getMessage(), new Object[]{}, request.getLocale()))
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(final Exception ex, final WebRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ko(ApiResponseStatus.SYSTEM, messageSource.getMessage(ex.getMessage(), new Object[]{}, request.getLocale()))
        );
    }

}
