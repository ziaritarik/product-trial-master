package com.althen.Ecommerce.exceptions;

import jakarta.servlet.ServletException;

public class JwtAuthenticationException extends ServletException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
