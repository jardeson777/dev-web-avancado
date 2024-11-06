package com.uff.espaco_aluno.model.repository;

import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.model.entity.StudentDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchoolRepository extends JpaRepository<School, UUID> {
    @Query("SELECT sd FROM StudentDiscipline sd")
    List<StudentDiscipline> findAllStatuses();

    Optional<School> findFirstByOrderByStartClassAsc();
}
