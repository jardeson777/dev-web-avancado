package com.uff.espaco_aluno.model.repository;

import com.uff.espaco_aluno.model.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DisciplineRepository extends JpaRepository<Discipline, UUID> {
    List<Discipline> findByTeacherId(String teacherId);
}
