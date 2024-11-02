package com.uff.espaco_aluno.model.dto;

import com.uff.espaco_aluno.model.entity.Teacher;
import com.uff.espaco_aluno.utils.enums.UserRole;

public record ValidateUserDto(String email, UserRole role, String name, String password) {

}
