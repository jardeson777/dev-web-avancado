package com.uff.espaco_aluno.repository;

import com.uff.espaco_aluno.model.entity.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CoordinatorRepository extends JpaRepository<Coordinator, UUID> {
    Optional<Coordinator> findByEmailAndPassword(String email, String password);
}
