package com.uff.espaco_aluno.model.entity;

import com.uff.espaco_aluno.config.anotations.UUIDGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity(name = "school")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UUIDGenerator
    @Column(nullable = false, columnDefinition = "char(36)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    private Date startClass;

    private Date endClass;

    private Date startRegistration;

    private Date endRegistration;
}

