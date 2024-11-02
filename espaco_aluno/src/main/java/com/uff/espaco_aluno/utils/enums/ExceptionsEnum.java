package com.uff.espaco_aluno.utils.enums;

import lombok.Getter;

@Getter
public enum ExceptionsEnum {
    EMAIL_PASSWORD_INVALID(401, "incorrect email or password"),
    USER_NOT_FOUND(404, "User not found."),
    UNREPORTED_ROLE(500, "Enter the user's role"),
    INVALID_ENUM_VALUE(400, "%s: Invalid value for enum. Accepted values are: %s"),
    NULL_INFO(500, "Create teacher does not allow null information."),
    INACTIVE_USER(401, "Activate the user before logging in");

    private final Integer statusCode;
    private final String description;

    ExceptionsEnum(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }
}
