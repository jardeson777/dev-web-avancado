package com.uff.espaco_aluno.model.dto.teacher;

import com.uff.espaco_aluno.model.entity.Teacher;

import java.util.UUID;

public record TeacherCreateDto(String email, UUID schoolId) {

    public Teacher newTeacher() {
        Teacher teacher = new Teacher();
        teacher.setEmail(email);
        teacher.setSchoolId(schoolId);
        return teacher;
    }
}
