package com.uff.espaco_aluno.usecase.student;


import com.uff.espaco_aluno.model.dto.student.StudentDashboardDto;
import com.uff.espaco_aluno.model.entity.*;
import com.uff.espaco_aluno.model.repository.StudentClassroomRepository;
import com.uff.espaco_aluno.model.repository.StudentDisciplineRepository;
import com.uff.espaco_aluno.model.repository.StudentRepository;
import com.uff.espaco_aluno.utils.enums.StudentStatusDIsciplineEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetDashboardStudentTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentDisciplineRepository studentDisciplineRepository;

    @Mock
    private StudentClassroomRepository studentClassroomRepository;

    @InjectMocks
    private GetDashboardStudent getDashboardStudent;

    private UUID studentId;
    private Student student;
    private School school;

    private Teacher teacher;
    private Discipline discipline;
    private Classroom classroom;
    private Classroom classroom2;
    private Classroom classroom3;

    private StudentDiscipline studentDiscipline;
    private StudentClassroom studentClassroom;
    private StudentClassroom studentClassroom2;
    private StudentClassroom studentClassroom3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentId = UUID.randomUUID();
        school = new School();
        school.setStartClass(LocalDate.now().minusDays(30));
        school.setEndClass(LocalDate.now().plusDays(60));
        school.setStartRegistration(LocalDate.now().minusMonths(2));
        school.setEndRegistration(LocalDate.now().plusMonths(1));

        student = new Student();
        student.setId(studentId);
        student.setSchool(school);
        student.setIsActive(true);

        teacher = new Teacher();
        teacher.setId(UUID.randomUUID());
        teacher.setSchool(school);
        teacher.setIsActive(true);

        discipline = new Discipline();
        discipline.setId(UUID.randomUUID());
        discipline.setTeacher(teacher);
        discipline.setName("Qualidade e teste de software");

        studentDiscipline = new StudentDiscipline();
        studentDiscipline.setId(UUID.randomUUID());
        studentDiscipline.setStatus(StudentStatusDIsciplineEnum.ENROLLED);
        studentDiscipline.setStudent(student);
        studentDiscipline.setDiscipline(discipline);

        classroom = new Classroom();
        classroom.setId(UUID.randomUUID());
        classroom.setDiscipline(discipline);
        classroom.setTitle("Aula 1");
        classroom.setDescription("Descrição");
        classroom.setDateTime(LocalDate.now());
        classroom.setWeight(1);

        classroom2 = new Classroom();
        classroom2.setId(UUID.randomUUID());
        classroom2.setDiscipline(discipline);
        classroom2.setTitle("Aula 2");
        classroom2.setDescription("Descrição");
        classroom2.setDateTime(LocalDate.now());
        classroom2.setWeight(2);

        classroom3 = new Classroom();
        classroom3.setId(UUID.randomUUID());
        classroom3.setDiscipline(discipline);
        classroom3.setTitle("Aula 3");
        classroom3.setDescription("Descrição");
        classroom3.setDateTime(LocalDate.now());
        classroom3.setWeight(1);

        studentClassroom = new StudentClassroom();
        studentClassroom.setId(UUID.randomUUID());
        studentClassroom.setClassroom(classroom);
        studentClassroom.setStudent(student);
        studentClassroom.setScore(10.0);
        studentClassroom.setPresence(true);

        studentClassroom2 = new StudentClassroom();
        studentClassroom2.setId(UUID.randomUUID());
        studentClassroom2.setClassroom(classroom2);
        studentClassroom2.setStudent(student);
        studentClassroom2.setScore(8.0);
        studentClassroom2.setPresence(true);

        studentClassroom3 = new StudentClassroom();
        studentClassroom3.setId(UUID.randomUUID());
        studentClassroom3.setClassroom(classroom3);
        studentClassroom3.setStudent(student);
        studentClassroom3.setScore(9.0);
        studentClassroom3.setPresence(true);
    }

    @Test
    void testExecute_withOneClassroom() {
        List<StudentDiscipline> studentDisciplines = List.of(studentDiscipline);

        List<StudentClassroom> studentClassrooms = List.of(studentClassroom);

        when(studentRepository.getReferenceById(studentId)).thenReturn(student);
        when(studentDisciplineRepository.findAllByStudent_Id(studentId)).thenReturn(studentDisciplines);
        when(studentClassroomRepository.findAllByStudent_id(studentId)).thenReturn(studentClassrooms);

        StudentDashboardDto dashboard = getDashboardStudent.execute(studentId);

        assertNotNull(dashboard);
        assertEquals(10.0, dashboard.averageGrade());
        assertEquals(1, dashboard.currentCourses());
        assertTrue(dashboard.termProgressPercentage() > 0);

        StudentDashboardDto.GradeDistribution gradeDistribution = dashboard.gradeDistribution();
        assertEquals(1, gradeDistribution.excellent());
        assertEquals(0, gradeDistribution.good());
        assertEquals(0, gradeDistribution.regular());
        assertEquals(0, gradeDistribution.insufficient());

        StudentDashboardDto.SchoolInfo schoolInfo = dashboard.schoolInfo();
        assertEquals(school.getStartClass(), schoolInfo.termStart());
        assertEquals(school.getEndClass(), schoolInfo.termEnd());
        assertEquals(school.getStartRegistration(), schoolInfo.enrollmentStart());
        assertEquals(school.getEndRegistration(), schoolInfo.enrollmentEnd());

        List<StudentDashboardDto.CoursePerformance> performances = dashboard.coursePerformances();
        assertEquals(1, performances.size());
        assertEquals("Qualidade e teste de software", performances.get(0).courseName());
        assertEquals(10.0, performances.get(0).grade());
        assertTrue(performances.get(0).attendance());
        assertEquals(StudentStatusDIsciplineEnum.ENROLLED, performances.get(0).status());

        verify(studentRepository).getReferenceById(studentId);
        verify(studentDisciplineRepository).findAllByStudent_Id(studentId);
        verify(studentClassroomRepository).findAllByStudent_id(studentId);
    }

    @Test
    void testExecute_withThreeClassroom() {
        List<StudentDiscipline> studentDisciplines = List.of(studentDiscipline);

        List<StudentClassroom> studentClassrooms = List.of(studentClassroom, studentClassroom2, studentClassroom3);

        when(studentRepository.getReferenceById(studentId)).thenReturn(student);
        when(studentDisciplineRepository.findAllByStudent_Id(studentId)).thenReturn(studentDisciplines);
        when(studentClassroomRepository.findAllByStudent_id(studentId)).thenReturn(studentClassrooms);

        StudentDashboardDto dashboard = getDashboardStudent.execute(studentId);

        assertNotNull(dashboard);

        assertEquals(8.75, dashboard.averageGrade());

        assertEquals(1, dashboard.currentCourses());

        assertTrue(dashboard.termProgressPercentage() > 0);

        StudentDashboardDto.GradeDistribution gradeDistribution = dashboard.gradeDistribution();
        assertEquals(0, gradeDistribution.excellent());
        assertEquals(1, gradeDistribution.good());
        assertEquals(0, gradeDistribution.regular());
        assertEquals(0, gradeDistribution.insufficient());

        StudentDashboardDto.SchoolInfo schoolInfo = dashboard.schoolInfo();
        assertEquals(school.getStartClass(), schoolInfo.termStart());
        assertEquals(school.getEndClass(), schoolInfo.termEnd());
        assertEquals(school.getStartRegistration(), schoolInfo.enrollmentStart());
        assertEquals(school.getEndRegistration(), schoolInfo.enrollmentEnd());

        List<StudentDashboardDto.CoursePerformance> performances = dashboard.coursePerformances();
        assertEquals(1, performances.size());
        assertEquals("Qualidade e teste de software", performances.get(0).courseName());
        assertEquals(7.67, performances.get(0).grade());
        assertTrue(performances.get(0).attendance());
        assertEquals(StudentStatusDIsciplineEnum.ENROLLED, performances.get(0).status());

        verify(studentRepository).getReferenceById(studentId);
        verify(studentDisciplineRepository).findAllByStudent_Id(studentId);
        verify(studentClassroomRepository).findAllByStudent_id(studentId);
    }

    @Test
    void testExecute_WithNoDisciplinesOrClassrooms() {
        when(studentRepository.getReferenceById(studentId)).thenReturn(student);
        when(studentDisciplineRepository.findAllByStudent_Id(studentId)).thenReturn(Collections.emptyList());
        when(studentClassroomRepository.findAllByStudent_id(studentId)).thenReturn(Collections.emptyList());

        StudentDashboardDto dashboard = getDashboardStudent.execute(studentId);

        long daysBetweenStartAndEndOfTerm = ChronoUnit.DAYS.between(student.getSchool().getStartClass(), student.getSchool().getEndClass());
        long daysFromClassStartUntilToday = ChronoUnit.DAYS.between(student.getSchool().getStartClass(), LocalDate.now());

        Double progress = (double) daysFromClassStartUntilToday / daysBetweenStartAndEndOfTerm * 100;
        progress = BigDecimal.valueOf(progress)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        assertNotNull(dashboard);
        assertEquals(0.0, dashboard.averageGrade());
        assertEquals(0, dashboard.currentCourses());
        assertEquals(progress, dashboard.termProgressPercentage());
        assertEquals(0, dashboard.gradeDistribution().excellent());
        assertEquals(0, dashboard.gradeDistribution().good());
        assertEquals(0, dashboard.gradeDistribution().regular());
        assertEquals(0, dashboard.gradeDistribution().insufficient());
        assertEquals(0, dashboard.coursePerformances().size());

        verify(studentRepository).getReferenceById(studentId);
        verify(studentDisciplineRepository).findAllByStudent_Id(studentId);
        verify(studentClassroomRepository).findAllByStudent_id(studentId);
    }
}
