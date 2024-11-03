package com.uff.espaco_aluno.model.dto;

import com.uff.espaco_aluno.model.entity.Coordinator;
import com.uff.espaco_aluno.model.entity.Student;
import com.uff.espaco_aluno.model.entity.Teacher;

import java.util.UUID;

public record UserResponseDto(UUID id, String name, String email, UUID SchoolId, String schoolName) {

    public static UserResponseDto mapCoordinatorToUserResponseDTO(Coordinator coordinator) {
        return new UserResponseDto(
                coordinator.getId(),
                coordinator.getName(),
                coordinator.getEmail(),
                coordinator.getSchool().getId(),
                coordinator.getSchool().getName()
        );
    }

    public static UserResponseDto mapTeacherToUserResponseDTO(Teacher teacher) {
        return new UserResponseDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getSchoolId(),
                teacher.getSchool().getName()
        );
    }

    public static UserResponseDto mapStudentToUserResponseDTO(Student student) {
        return new UserResponseDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getSchoolId(),
                student.getSchool().getName()
        );
    }
}
