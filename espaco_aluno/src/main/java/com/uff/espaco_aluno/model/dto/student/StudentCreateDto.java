package com.uff.espaco_aluno.model.dto.student;

import com.uff.espaco_aluno.model.entity.Student;

import java.util.UUID;

public record StudentCreateDto(String email, UUID schoolId) {

    public Student newStudent() {
        Student student = new Student();
        student.setEmail(email);
        student.setSchoolId(schoolId);
        return student;
    }
}
