package com.uff.espaco_aluno.repository;

import com.uff.espaco_aluno.model.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DisciplineRepository extends JpaRepository<Discipline, UUID> {
}
