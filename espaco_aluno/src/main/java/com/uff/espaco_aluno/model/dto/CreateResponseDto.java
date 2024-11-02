package com.uff.espaco_aluno.model.dto;

import java.util.UUID;

public record CreateResponseDto(UUID id) {

    public static CreateResponseDto newUser(UUID id) {
        return new CreateResponseDto(id);
    }
}
