package com.uff.espaco_aluno.model.dto.teacher;

import com.uff.espaco_aluno.model.entity.Teacher;

import java.util.UUID;

public record TeacherCreateDto(String email, Boolean isActive, UUID schoolId) {

    public Teacher newTeacher() {
        Teacher teacher = new Teacher();
        teacher.setEmail(email);
        teacher.setIsActive(isActive);
        teacher.setSchoolId(schoolId);
        return teacher;
    }
}
