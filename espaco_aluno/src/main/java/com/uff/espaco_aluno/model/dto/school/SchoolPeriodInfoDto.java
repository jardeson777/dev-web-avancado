package com.uff.espaco_aluno.model.dto.school;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SchoolPeriodInfoDto {
    private LocalDate startClass;
    private LocalDate endClass;
    private LocalDate startRegistration;
    private LocalDate endRegistration;

    public SchoolPeriodInfoDto(LocalDate startClass, LocalDate endClass, LocalDate startRegistration, LocalDate endRegistration) {
        this.startClass = startClass;
        this.endClass = endClass;
        this.startRegistration = startRegistration;
        this.endRegistration = endRegistration;
    }

    // Getters e Setters
}

