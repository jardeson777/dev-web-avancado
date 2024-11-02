package com.uff.espaco_aluno.controller;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.exception.ErrorResponse;
import com.uff.espaco_aluno.exception.InvalidUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoginException(InvalidLoginException ex) {
        ErrorResponse errorResponse = ex.getErrorResponse();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponse> handleInvalidGetUserException(InvalidUserException ex) {
        ErrorResponse errorResponse = ex.getErrorResponse();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
