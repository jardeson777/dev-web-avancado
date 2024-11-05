package com.uff.espaco_aluno.model.repository;

import com.uff.espaco_aluno.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchoolRepository extends JpaRepository<School, UUID> {
}
