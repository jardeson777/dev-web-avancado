package com.uff.espaco_aluno.model.entity;

import com.uff.espaco_aluno.utils.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
