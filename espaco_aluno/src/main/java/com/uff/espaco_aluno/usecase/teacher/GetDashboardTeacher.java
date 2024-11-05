package com.uff.espaco_aluno.usecase.teacher;

import com.uff.espaco_aluno.model.dto.teacher.TeacherDashboardDTO;
import com.uff.espaco_aluno.model.entity.Classroom;
import com.uff.espaco_aluno.model.entity.Discipline;
import com.uff.espaco_aluno.model.entity.StudentClassroom;
import com.uff.espaco_aluno.model.repository.ClassroomRepository;
import com.uff.espaco_aluno.model.repository.DisciplineRepository;
import com.uff.espaco_aluno.model.repository.StudentClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GetDashboardTeacher {

    private final DisciplineRepository disciplineRepository;
    private final ClassroomRepository classroomRepository;
    private final StudentClassroomRepository studentClassroomRepository;

    @Autowired
    public GetDashboardTeacher(DisciplineRepository disciplineRepository,
                                      ClassroomRepository classroomRepository,
                                      StudentClassroomRepository studentClassroomRepository) {
        this.disciplineRepository = disciplineRepository;
        this.classroomRepository = classroomRepository;
        this.studentClassroomRepository = studentClassroomRepository;
    }

    public TeacherDashboardDTO execute(String teacherId) {
        List<Discipline> disciplines = disciplineRepository.findByTeacherId(teacherId);

        List<Classroom> classrooms = classroomRepository.findByTeacherId(teacherId);
        int totalClasses = classrooms.size();
        int conductedClasses = (int) classrooms.stream().filter(Classroom::isConducted).count();

        double attendanceAverage = calculateAttendanceAverage(classrooms);

        double classAverage = calculateClassAverage(classrooms);
        Map<String, Long> gradeDistribution = getGradeDistribution(classrooms);

        int totalStudents = studentClassroomRepository.countStudentsInClassrooms(classrooms);
        int approvedStudents = studentClassroomRepository.countApprovedStudents(classrooms);
        double approvalRate = ((double) approvedStudents / totalStudents) * 100;
        int remainingClasses = totalClasses - conductedClasses;

        return new TeacherDashboardDTO(
                totalClasses,
                conductedClasses,
                attendanceAverage,
                classAverage,
                gradeDistribution,
                totalStudents,
                approvedStudents,
                approvalRate,
                remainingClasses
        );
    }

    private double calculateAttendanceAverage(List<Classroom> classrooms) {
        List<StudentClassroom> allAttendances = studentClassroomRepository.findAllByClassrooms(classrooms);
        long presentCount = allAttendances.stream().filter(StudentClassroom::isPresence).count();
        return ((double) presentCount / allAttendances.size()) * 100;
    }

    private double calculateClassAverage(List<Classroom> classrooms) {
        List<StudentClassroom> allScores = studentClassroomRepository.findAllByClassrooms(classrooms);
        return allScores.stream()
                .filter(sc -> sc.getScore() != null)
                .mapToDouble(StudentClassroom::getScore)
                .average()
                .orElse(0.0);
    }

    private Map<String, Long> getGradeDistribution(List<Classroom> classrooms) {
        List<StudentClassroom> allScores = studentClassroomRepository.findAllByClassrooms(classrooms);
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

