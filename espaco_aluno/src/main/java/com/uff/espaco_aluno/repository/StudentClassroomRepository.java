package com.uff.espaco_aluno.repository;

import com.uff.espaco_aluno.model.entity.StudentClassroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, UUID> {

    @Query("SELECT sc FROM student_classroom sc WHERE sc.student.id = :studentId")
    List<StudentClassroom> findAllByStudent_id(UUID studentId);
}
