package com.uff.espaco_aluno.model.dto;

public record ResponseTokenDto(String token) {

    public static ResponseTokenDto newToken(String token) {
        return new ResponseTokenDto((token));
    }
}
