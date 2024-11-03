package com.uff.espaco_aluno.model.dto.teacher;

import com.uff.espaco_aluno.model.entity.Teacher;

import java.util.UUID;

public record TeacherCreateDto(String email, String name, String password, UUID schoolId) {

    public Teacher newTeacher() {
        Teacher teacher = new Teacher();
        teacher.setEmail(email);
        teacher.setName(name);
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setSchoolId(schoolId);
        return teacher;
    }
}
