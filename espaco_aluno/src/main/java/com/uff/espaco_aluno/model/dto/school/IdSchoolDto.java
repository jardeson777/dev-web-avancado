package com.uff.espaco_aluno.model.dto.school;

import java.util.UUID;

public record IdSchoolDto(UUID id) {

    public static IdSchoolDto newIdSchoolDto(UUID id) {
        return new IdSchoolDto(id);
    }
}
