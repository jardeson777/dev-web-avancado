package com.uff.espaco_aluno.usecase.coordinator;

import com.uff.espaco_aluno.model.dto.coordinator.CoordinatorDashboardDto;
import com.uff.espaco_aluno.model.dto.school.SchoolPeriodInfoDto;
import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.model.entity.StudentClassroom;
import com.uff.espaco_aluno.model.entity.StudentDiscipline;
import com.uff.espaco_aluno.model.repository.*;
import com.uff.espaco_aluno.utils.enums.StudentStatusDIsciplineEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GetCoordinatorDashboard {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentClassroomRepository studentClassroomRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public GetCoordinatorDashboard(StudentRepository studentRepository,
                                   TeacherRepository teacherRepository,
                                   DisciplineRepository disciplineRepository,
                                   StudentClassroomRepository studentClassroomRepository,
                                   SchoolRepository schoolRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.disciplineRepository = disciplineRepository;
        this.studentClassroomRepository = studentClassroomRepository;
        this.schoolRepository = schoolRepository;
    }

    public CoordinatorDashboardDto execute() {
        long totalStudents = studentRepository.count();
        long totalTeachers = teacherRepository.count();
        long totalDisciplines = disciplineRepository.count();

        double attendanceAverage = calculateAttendanceAverage();

        double gradeAverage = calculateGradeAverage();

        Map<String, Long> statusDistribution = getStatusDistribution();

        Map<String, Long> gradeDistribution = getGradeDistribution();

        School school = schoolRepository.findFirstByOrderByStartClassAsc()
                .orElseThrow(() -> new EntityNotFoundException("School data not found"));

        SchoolPeriodInfoDto periodInfo = new SchoolPeriodInfoDto(
                school.getStartClass(), school.getEndClass(),
                school.getStartRegistration(), school.getEndRegistration()
        );

        return new CoordinatorDashboardDto(
                totalStudents, totalTeachers, totalDisciplines,
                attendanceAverage, gradeAverage,
                statusDistribution, gradeDistribution,
                periodInfo
        );
    }

    public double calculateAttendanceAverage() {
        List<StudentClassroom> allAttendances = studentClassroomRepository.findAll();
        long presentCount = allAttendances.stream().filter(StudentClassroom::isPresence).count();
        return ((double) presentCount / allAttendances.size()) * 100;
    }

    public double calculateGradeAverage() {
        List<StudentClassroom> allScores = studentClassroomRepository.findAll();
        return allScores.stream()
                .filter(sc -> sc.getScore() != null)
                .mapToDouble(StudentClassroom::getScore)
                .average()
                .orElse(0.0);
    }

    public Map<String, Long> getStatusDistribution() {
        List<StudentDiscipline> studentDisciplines = studentRepository.findAllStatuses();
        return studentDisciplines.stream()
                .map(sd -> {
                    if (StudentStatusDIsciplineEnum.FAILED_DUE_TO_ABSENCE.toString().equalsIgnoreCase(sd.getStatus().toString())) return "Reprovado por Falta";
                    if (StudentStatusDIsciplineEnum.FAILED.toString().equalsIgnoreCase(sd.getStatus().toString())) return "Reprovado";
                    if (StudentStatusDIsciplineEnum.APPROVED.toString().equalsIgnoreCase(sd.getStatus().toString())) return "Aprovado";
                    return "Cursando";
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public Map<String, Long> getGradeDistribution() {
        List<StudentClassroom> allScores = studentClassroomRepository.findAll();
        return allScores.stream()
                .filter(sc -> sc.getScore() != null)
                .map(sc -> {
                    double score = sc.getScore();
                    if (score >= 9) return "Excelente (9-10)";
                    if (score >= 7) return "Bom (7-8.9)";
                    if (score >= 5) return "Regular (5-6.9)";
                    return "Insuficiente (<5)";
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}