package com.uff.espaco_aluno.repository;

import com.uff.espaco_aluno.model.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
}
