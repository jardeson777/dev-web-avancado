package com.uff.espaco_aluno.model.entity;

import com.uff.espaco_aluno.config.anotations.UUIDGenerator;
import com.uff.espaco_aluno.utils.enums.StudentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "student_discipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UUIDGenerator
    @Column(nullable = false, columnDefinition = "char(36)")
    private UUID id;

    @Column(nullable = false)
    private StudentType studentType;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;
}
