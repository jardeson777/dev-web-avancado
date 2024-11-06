package com.uff.espaco_aluno.model.repository;

import com.uff.espaco_aluno.model.entity.Student;
import com.uff.espaco_aluno.model.entity.StudentDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findByEmailAndPassword(String email, String password);

    Optional<Student> findByEmail(String email);

    @Query("SELECT sd FROM StudentDiscipline sd")
    List<StudentDiscipline> findAllStatuses();
}
