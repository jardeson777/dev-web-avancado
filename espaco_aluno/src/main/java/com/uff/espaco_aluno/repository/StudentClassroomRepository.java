package com.uff.espaco_aluno.repository;

import com.uff.espaco_aluno.model.entity.StudentClassroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, UUID> {

    List<StudentClassroom> findByStudent_id(UUID studentId);
}
