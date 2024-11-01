package com.uff.espaco_aluno.model.entity;

import com.uff.espaco_aluno.utils.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "teacher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends User {

    @Transient
    private UserRole role = UserRole.TEACHER;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
}
