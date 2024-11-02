package com.uff.espaco_aluno.exception;

import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import lombok.Getter;

@Getter
public class InvalidLoginException extends Exception {
    private final ErrorResponse errorResponse;

    public InvalidLoginException() {
        super(ExceptionsEnum.EMAIL_PASSWORD_INVALID.getDescription());
        this.errorResponse = new ErrorResponse(
                ExceptionsEnum.EMAIL_PASSWORD_INVALID.getStatusCode(),
                ExceptionsEnum.EMAIL_PASSWORD_INVALID.getDescription()
        );
    }

    public InvalidLoginException(ExceptionsEnum exceptionsEnum) {
        this.errorResponse = new ErrorResponse(
                exceptionsEnum.getStatusCode(),
                exceptionsEnum.getDescription()
        );
    }

}
