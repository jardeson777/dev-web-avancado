package com.uff.espaco_aluno.model.entity;

import com.uff.espaco_aluno.config.anotations.UUIDGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "student_classroom")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentClassroom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UUIDGenerator
    @Column(nullable = false, columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    private float score;
    private boolean presence;
}

