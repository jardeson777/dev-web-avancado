package com.uff.espaco_aluno.exception;

public class ErrorResponse {
    private int statusCode;
    private String description;

    public ErrorResponse(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }
}
