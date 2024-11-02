package com.uff.espaco_aluno.model.dto.coordinator;

import java.util.UUID;

public record CoordinatorCreateDto(String name, String email, String password, UUID idSchool, String schoolName) {
}
