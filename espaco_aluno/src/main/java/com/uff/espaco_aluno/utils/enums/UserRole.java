package com.uff.espaco_aluno.utils.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    COORDINATOR(0, "coordinator"),
    STUDENT(1, "student"),
    TEACHER(2, "teacher");

    private Integer code;
    private String name;

    UserRole(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
