package com.tripod.homeloansystem.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.tripod.homeloansystem.exceptions.TokenRefreshException;
@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return ex.getMessage();
    }
}