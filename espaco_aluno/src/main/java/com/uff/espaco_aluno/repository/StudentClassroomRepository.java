package com.uff.espaco_aluno.repository;

import com.uff.espaco_aluno.model.entity.StudentClassroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, UUID> {
}
