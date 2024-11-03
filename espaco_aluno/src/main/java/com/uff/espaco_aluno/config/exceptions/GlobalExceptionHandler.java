package com.uff.espaco_aluno.config.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.uff.espaco_aluno.exception.ErrorResponse;
import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.exception.InvalidUserException;
import com.uff.espaco_aluno.exception.SchoolNotFoundException;
import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // Captura todas as exceções
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse;

        if (ex instanceof InvalidLoginException) {
            errorResponse = ((InvalidLoginException) ex).getErrorResponse();
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        } else if (ex instanceof InvalidUserException) {
            errorResponse = ((InvalidUserException) ex).getErrorResponse();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } else if (ex instanceof SchoolNotFoundException) {
            errorResponse = ((SchoolNotFoundException) ex).getErrorResponse();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEnumException(InvalidFormatException ex) {
        if (ex.getTargetType().isEnum()) {
            String enumName = ex.getTargetType().getSimpleName();

            String acceptedValues = Arrays.stream(ex.getTargetType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            String description = String.format(ExceptionsEnum.INVALID_ENUM_VALUE.getDescription(), enumName, acceptedValues);

            ErrorResponse errorResponse = new ErrorResponse(ExceptionsEnum.INVALID_ENUM_VALUE.getStatusCode(), description);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        ErrorResponse genericErrorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid format.");
        return new ResponseEntity<>(genericErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
