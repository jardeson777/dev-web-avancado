package com.uff.espaco_aluno.exception;

import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import lombok.Getter;

@Getter
public class NullInfoException extends Exception{
    private final ErrorResponse errorResponse;

    public NullInfoException() {
        super(ExceptionsEnum.NULL_INFO.getDescription());
        this.errorResponse = new ErrorResponse(
                ExceptionsEnum.NULL_INFO.getStatusCode(),
                ExceptionsEnum.NULL_INFO.getDescription()
        );
    }
}
