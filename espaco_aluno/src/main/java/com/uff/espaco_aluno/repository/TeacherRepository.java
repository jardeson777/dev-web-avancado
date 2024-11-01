package com.uff.espaco_aluno.repository;

import com.uff.espaco_aluno.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}
