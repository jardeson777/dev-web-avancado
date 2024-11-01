package com.uff.espaco_aluno.model.entity;

import com.uff.espaco_aluno.utils.enums.StudentType;
import com.uff.espaco_aluno.utils.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User {

    @Transient
    private UserRole role = UserRole.STUDENT;
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
}
