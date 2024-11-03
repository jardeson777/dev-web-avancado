package com.uff.espaco_aluno.model.dto.student;

import com.uff.espaco_aluno.model.entity.Student;

import java.util.UUID;

public record StudentCreateDto(String email, String name, String password, UUID schoolId) {

    public Student newStudent() {
        Student student = new Student();
        student.setEmail(email);
        student.setName(name);
        student.setPassword(password);
        student.setSchoolId(schoolId);
        return student;
    }
}
