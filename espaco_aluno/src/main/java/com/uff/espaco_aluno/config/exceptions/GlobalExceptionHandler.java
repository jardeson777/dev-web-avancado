package com.uff.espaco_aluno.config.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.uff.espaco_aluno.exception.ErrorResponse;
import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.exception.InvalidUserException;
import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

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
