package com.uff.espaco_aluno.exception;

import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import lombok.Getter;

@Getter
public class SchoolNotFoundException extends Exception {

    private final ErrorResponse errorResponse;

    public SchoolNotFoundException() {
        super(ExceptionsEnum.SCHOOL_NOT_FOUND.getDescription());
        this.errorResponse = new ErrorResponse(
                ExceptionsEnum.SCHOOL_NOT_FOUND.getStatusCode(),
                ExceptionsEnum.SCHOOL_NOT_FOUND.getDescription()
        );
    }
}
