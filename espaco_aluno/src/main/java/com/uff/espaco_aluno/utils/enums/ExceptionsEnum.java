package com.uff.espaco_aluno.utils.enums;

public enum ExceptionsEnum {
    EMAIL_PASSWORD_INVALID(401, "incorrect email or password"),
    USER_NOT_FOUND(404, "User not found.");

    private final Integer statusCode;
    private final String description;

    // Construtor do enum
    ExceptionsEnum(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    // Getter para o id
    public Integer getStatusCode() {
        return statusCode;
    }

    // Getter para a descrição
    public String getDescription() {
        return description;
    }
}
