package com.uff.espaco_aluno.model.dto;

import com.uff.espaco_aluno.utils.enums.UserRole;

public record LoginDto (String email, String password, UserRole role) {
}
