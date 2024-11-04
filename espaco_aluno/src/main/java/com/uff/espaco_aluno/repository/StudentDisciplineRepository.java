package com.uff.espaco_aluno.repository;

import com.uff.espaco_aluno.model.entity.StudentDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentDisciplineRepository extends JpaRepository<StudentDiscipline, UUID> {

    List<StudentDiscipline> findAllByStudent_Id(UUID studentId);
}
