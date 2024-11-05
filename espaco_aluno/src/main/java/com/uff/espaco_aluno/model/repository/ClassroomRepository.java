package com.uff.espaco_aluno.model.repository;

import com.uff.espaco_aluno.model.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
    List<Classroom> findByTeacherId(String teacherId);
}
