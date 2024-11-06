package com.uff.espaco_aluno.usecase.student;

import com.uff.espaco_aluno.model.dto.student.StudentDashboardDto;
import com.uff.espaco_aluno.model.entity.Classroom;
import com.uff.espaco_aluno.model.entity.Student;
import com.uff.espaco_aluno.model.entity.StudentClassroom;
import com.uff.espaco_aluno.model.entity.StudentDiscipline;
import com.uff.espaco_aluno.model.repository.StudentClassroomRepository;
import com.uff.espaco_aluno.model.repository.StudentDisciplineRepository;
import com.uff.espaco_aluno.model.repository.StudentRepository;
import com.uff.espaco_aluno.utils.enums.StudentStatusDIsciplineEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GetDashboardStudent {

    @Autowired
    StudentRepository repository;

    @Autowired
    StudentDisciplineRepository studentDisciplineRepository;

    @Autowired
    StudentClassroomRepository studentClassroomRepository;

    public StudentDashboardDto execute(UUID id) {

        Student student = repository.getReferenceById(id);
        List<StudentDiscipline> studentDisciplines = studentDisciplineRepository.findAllByStudent_Id(id);
        List<StudentClassroom> studentClassrooms = studentClassroomRepository.findAllByStudent_id(id);

        Map<UUID, Double> averageByDiscipline = getClassAverage(studentClassrooms);

        return new StudentDashboardDto(
                formatDouble(calculateAverageGrade(studentClassrooms)),
                getCurrentCourses(studentDisciplines),
                formatDouble(calculateTermProgressPercentage(student)),
                getGradeDistribution(averageByDiscipline),
                getSchoolInfo(student),
                getCoursePerformances(studentDisciplines, studentClassrooms, averageByDiscipline)
        );
    }

    private double calculateAverageGrade(List<StudentClassroom> studentClassrooms) {
        double totalWeightedScore = studentClassrooms.stream()
                .mapToDouble(sc -> sc.getScore() * Math.max(sc.getClassroom().getWeight(), 1))
                .sum();

        int totalWeight = studentClassrooms.stream()
                .mapToInt(sc -> Math.max(sc.getClassroom().getWeight(), 1))
                .sum();

        return totalWeight == 0 ? 0 : totalWeightedScore / totalWeight;
    }

    private int getCurrentCourses(List<StudentDiscipline> studentDisciplines) {
        return (int) studentDisciplines.stream()
                .filter(studentDiscipline -> studentDiscipline.getStatus() == StudentStatusDIsciplineEnum.ENROLLED)
                .count();
    }

    private double calculateTermProgressPercentage(Student student) {
        LocalDate now = LocalDate.now();
        long daysBetweenStartAndEndOfTerm = ChronoUnit.DAYS.between(student.getSchool().getStartClass(), student.getSchool().getEndClass());
        long daysFromClassStartUntilToday = ChronoUnit.DAYS.between(student.getSchool().getStartClass(), now);

        if (daysFromClassStartUntilToday <= 0 || daysFromClassStartUntilToday > daysBetweenStartAndEndOfTerm) {
            return 0;
        } else {
            return (double) daysFromClassStartUntilToday / daysBetweenStartAndEndOfTerm * 100;
        }
    }

    private Map<UUID, Double> getClassAverage(List<StudentClassroom> studentClassrooms) {
        return studentClassrooms.stream()
                .collect(Collectors.groupingBy(
                        studentClassroom -> studentClassroom.getClassroom().getDiscipline().getId(),
                        Collectors.collectingAndThen(
                                Collectors.averagingDouble(this::calculateStudentClassroomAverage),
                                this::formatDouble
                        )
                ));
    }

    private double calculateStudentClassroomAverage(StudentClassroom studentClassroom) {
        Classroom classroom = studentClassroom.getClassroom();
        int weight = Math.max(classroom.getWeight(), 1);
        return studentClassroom.getScore() / weight;
    }

    private List<StudentDashboardDto.CoursePerformance> getCoursePerformances(List<StudentDiscipline> studentDisciplines, List<StudentClassroom> studentClassrooms, Map<UUID, Double> averageByDiscipline) {
        return studentDisciplines.stream()
                .map(studentDiscipline -> {
                    double average = averageByDiscipline.getOrDefault(studentDiscipline.getDiscipline().getId(), 0.0);
                    boolean presence = studentClassrooms.stream()
                            .filter(sc -> sc.getClassroom().getDiscipline().getId().equals(studentDiscipline.getDiscipline().getId()))
                            .map(StudentClassroom::getPresence)
                            .findFirst()
                            .orElse(false);
                    StudentStatusDIsciplineEnum status = studentDiscipline.getStatus();

                    return new StudentDashboardDto.CoursePerformance(
                            studentDiscipline.getDiscipline().getName(),
                            formatDouble(average),
                            presence,
                            status
                    );
                })
                .collect(Collectors.toList());
    }

    private StudentDashboardDto.GradeDistribution getGradeDistribution(Map<UUID, Double> averageByDiscipline) {
        return new StudentDashboardDto.GradeDistribution(
                (int) averageByDiscipline.values().stream().filter(score -> score > 9).count(),
                (int) averageByDiscipline.values().stream().filter(score -> score >= 7 && score <= 9).count(),
                (int) averageByDiscipline.values().stream().filter(score -> score >= 5 && score <= 7).count(),
                (int) averageByDiscipline.values().stream().filter(score -> score < 5).count()
        );
    }

    private StudentDashboardDto.SchoolInfo getSchoolInfo(Student student) {
        return new StudentDashboardDto.SchoolInfo(
                student.getSchool().getStartClass(),
                student.getSchool().getEndClass(),
                student.getSchool().getStartRegistration(),
                student.getSchool().getEndRegistration()
        );
    }

    private double formatDouble(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
