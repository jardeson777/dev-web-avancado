package com.uff.espaco_aluno.usecase.coordinator;
import com.uff.espaco_aluno.model.dto.coordinator.CoordinatorDashboardDto;
import com.uff.espaco_aluno.model.dto.school.SchoolPeriodInfoDto;
import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.model.entity.StudentClassroom;
import com.uff.espaco_aluno.model.entity.StudentDiscipline;
import com.uff.espaco_aluno.model.repository.*;
import com.uff.espaco_aluno.utils.enums.StudentStatusDIsciplineEnum;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetCoordinatorDashboardTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private StudentClassroomRepository studentClassroomRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private GetCoordinatorDashboard getCoordinatorDashboard;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void execute_shouldReturnCoordinatorDashboardDto() {
        when(studentRepository.count()).thenReturn(1200L);
        when(teacherRepository.count()).thenReturn(80L);
        when(disciplineRepository.count()).thenReturn(50L);

        StudentClassroom studentClassroom1 = new StudentClassroom();
        studentClassroom1.setPresence(true);
        studentClassroom1.setScore(8.5);

        StudentClassroom studentClassroom2 = new StudentClassroom();
        studentClassroom2.setPresence(false);
        studentClassroom2.setScore(9.0);

        when(studentClassroomRepository.findAll()).thenReturn(List.of(studentClassroom1, studentClassroom2));

        StudentDiscipline studentDiscipline1 = new StudentDiscipline();
        studentDiscipline1.setStatus(StudentStatusDIsciplineEnum.APPROVED);

        StudentDiscipline studentDiscipline2 = new StudentDiscipline();
        studentDiscipline2.setStatus(StudentStatusDIsciplineEnum.FAILED_DUE_TO_ABSENCE);

        when(studentRepository.findAllStatuses()).thenReturn(List.of(studentDiscipline1, studentDiscipline2));

        School school = new School();
        school.setStartClass(LocalDate.parse("2024-01-01"));
        school.setEndClass(LocalDate.parse("2024-06-30"));
        school.setStartRegistration(LocalDate.parse("2024-01-10"));
        school.setEndRegistration(LocalDate.parse("2024-01-20"));

        when(schoolRepository.findFirstByOrderByStartClassAsc()).thenReturn(Optional.of(school));

        CoordinatorDashboardDto result = getCoordinatorDashboard.execute();

        assertEquals(1200L, result.getTotalStudents());
        assertEquals(80L, result.getTotalTeachers());
        assertEquals(50L, result.getTotalDisciplines());
        assertEquals(50.0, result.getAttendanceAverage());
        assertEquals(8.75, result.getGradeAverage());
        assertNotNull(result.getStatusDistribution());
        assertNotNull(result.getGradeDistribution());

        SchoolPeriodInfoDto periodInfo = result.getPeriodInfo();
        assertEquals(LocalDate.parse("2024-01-01"), periodInfo.getStartClass());
        assertEquals(LocalDate.parse("2024-06-30"), periodInfo.getEndClass());
        assertEquals(LocalDate.parse("2024-01-10"), periodInfo.getStartRegistration());
        assertEquals(LocalDate.parse("2024-01-20"), periodInfo.getEndRegistration());
    }

    @Test
    public void execute_shouldThrowEntityNotFoundException_whenNoSchoolFound() {
        when(schoolRepository.findFirstByOrderByStartClassAsc()).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> getCoordinatorDashboard.execute());
    }

    @Test
    public void calculateAttendanceAverage_shouldReturnCorrectValue() {
        StudentClassroom studentClassroom1 = new StudentClassroom();
        studentClassroom1.setPresence(true);

        StudentClassroom studentClassroom2 = new StudentClassroom();
        studentClassroom2.setPresence(false);

        StudentClassroom studentClassroom3 = new StudentClassroom();
        studentClassroom3.setPresence(true);

        when(studentClassroomRepository.findAll()).thenReturn(List.of(studentClassroom1, studentClassroom2, studentClassroom3));

        double attendanceAverage = getCoordinatorDashboard.calculateAttendanceAverage();

        assertEquals(66.67, attendanceAverage, 0.01);
    }

    @Test
    public void calculateGradeAverage_shouldReturnCorrectValue() {
        StudentClassroom studentClassroom1 = new StudentClassroom();
        studentClassroom1.setScore(7.5);

        StudentClassroom studentClassroom2 = new StudentClassroom();
        studentClassroom2.setScore(8.5);

        StudentClassroom studentClassroom3 = new StudentClassroom();
        studentClassroom3.setScore(null); // Should be ignored in average calculation

        when(studentClassroomRepository.findAll()).thenReturn(List.of(studentClassroom1, studentClassroom2, studentClassroom3));

        double gradeAverage = getCoordinatorDashboard.calculateGradeAverage();

        assertEquals(8.0, gradeAverage, 0.01);
    }

    @Test
    public void getStatusDistribution_shouldReturnCorrectDistribution() {
        StudentDiscipline studentDiscipline1 = new StudentDiscipline();
        studentDiscipline1.setStatus(StudentStatusDIsciplineEnum.APPROVED);

        StudentDiscipline studentDiscipline2 = new StudentDiscipline();
        studentDiscipline2.setStatus(StudentStatusDIsciplineEnum.FAILED);

        StudentDiscipline studentDiscipline3 = new StudentDiscipline();
        studentDiscipline3.setStatus(StudentStatusDIsciplineEnum.FAILED_DUE_TO_ABSENCE);

        StudentDiscipline studentDiscipline4 = new StudentDiscipline();
        studentDiscipline4.setStatus(StudentStatusDIsciplineEnum.ENROLLED);

        when(studentRepository.findAllStatuses()).thenReturn(List.of(studentDiscipline1, studentDiscipline2, studentDiscipline3, studentDiscipline4));

        Map<String, Long> statusDistribution = getCoordinatorDashboard.getStatusDistribution();

        assertEquals(1, statusDistribution.get("Aprovado"));
        assertEquals(1, statusDistribution.get("Reprovado"));
        assertEquals(1, statusDistribution.get("Reprovado por Falta"));
        assertEquals(1, statusDistribution.get("Cursando"));
    }

    @Test
    public void getGradeDistribution_shouldReturnCorrectDistribution() {
        StudentClassroom studentClassroom1 = new StudentClassroom();
        studentClassroom1.setScore(9.5);

        StudentClassroom studentClassroom2 = new StudentClassroom();
        studentClassroom2.setScore(7.8);

        StudentClassroom studentClassroom3 = new StudentClassroom();
        studentClassroom3.setScore(5.5);

        StudentClassroom studentClassroom4 = new StudentClassroom();
        studentClassroom4.setScore(4.0);

        when(studentClassroomRepository.findAll()).thenReturn(List.of(studentClassroom1, studentClassroom2, studentClassroom3, studentClassroom4));

        Map<String, Long> gradeDistribution = getCoordinatorDashboard.getGradeDistribution();

        assertEquals(1, gradeDistribution.get("Excelente (9-10)"));
        assertEquals(1, gradeDistribution.get("Bom (7-8.9)"));
        assertEquals(1, gradeDistribution.get("Regular (5-6.9)"));
        assertEquals(1, gradeDistribution.get("Insuficiente (<5)"));
    }
}