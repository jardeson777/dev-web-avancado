package com.uff.espaco_aluno.model.entity;

import com.uff.espaco_aluno.utils.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "coordinator")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinator extends User {

    @Transient
    private UserRole role = UserRole.COORDINATOR;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Column(name = "school_id", nullable = false, insertable = false, updatable = false)
    private UUID schoolId;
}
