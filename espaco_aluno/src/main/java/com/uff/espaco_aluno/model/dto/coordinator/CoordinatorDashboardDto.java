package com.uff.espaco_aluno.model.dto.coordinator;

import com.uff.espaco_aluno.model.dto.school.SchoolPeriodInfoDto;
import lombok.Getter;

import java.util.Map;

@Getter
public class CoordinatorDashboardDto {
    private long totalStudents;
    private long totalTeachers;
    private long totalDisciplines;
    private double attendanceAverage;
    private double gradeAverage;
    private Map<String, Long> statusDistribution;
    private Map<String, Long> gradeDistribution;
    private SchoolPeriodInfoDto periodInfo;

    public CoordinatorDashboardDto(long totalStudents, long totalTeachers, long totalDisciplines,
                                   double attendanceAverage, double gradeAverage,
                                   Map<String, Long> statusDistribution, Map<String, Long> gradeDistribution,
                                   SchoolPeriodInfoDto periodInfo) {
        this.totalStudents = totalStudents;
        this.totalTeachers = totalTeachers;
        this.totalDisciplines = totalDisciplines;
        this.attendanceAverage = attendanceAverage;
        this.gradeAverage = gradeAverage;
        this.statusDistribution = statusDistribution;
        this.gradeDistribution = gradeDistribution;
        this.periodInfo = periodInfo;
    }
}

