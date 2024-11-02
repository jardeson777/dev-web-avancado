package com.uff.espaco_aluno.exception;

import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import lombok.Getter;

@Getter
public class InvalidUserException extends Exception {
    private final ErrorResponse errorResponse;

    public InvalidUserException() {
        super(ExceptionsEnum.EMAIL_PASSWORD_INVALID.getDescription());
        this.errorResponse = new ErrorResponse(
                ExceptionsEnum.USER_NOT_FOUND.getStatusCode(),
                ExceptionsEnum.USER_NOT_FOUND.getDescription()
        );
    }

}
