package com.uff.espaco_aluno.utils.enums;

public enum StudentStatusDIsciplineEnum {
    ENROLLED("Enrolled"),
    OBSERVER("Observer"),
    APPROVED("Approved"),
    FAILED("Failed"),
    FAILED_DUE_TO_ABSENCE("Failed due to absence");

    private final String description;

    StudentStatusDIsciplineEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
