package com.uff.espaco_aluno.model.dto.student;

import com.uff.espaco_aluno.utils.enums.StudentStatusDIsciplineEnum;

import java.time.LocalDate;
import java.util.List;

public record StudentDashboardDto(
        Double averageGrade,
        Integer currentCourses,
        Double termProgressPercentage,
        GradeDistribution gradeDistribution,
        SchoolInfo schoolInfo,
        List<CoursePerformance> coursePerformances
) {
    public record GradeDistribution(
            Integer excellent,
            Integer good,
            Integer regular,
            Integer insufficient
    ) {}

    public record SchoolInfo(
            LocalDate termStart,
            LocalDate termEnd,
            LocalDate enrollmentStart,
            LocalDate enrollmentEnd
    ) {}

    public record CoursePerformance(
            String courseName,
            double grade,
            Boolean attendance,
            StudentStatusDIsciplineEnum status
    ) {}
}
