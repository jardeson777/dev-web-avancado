package com.uff.espaco_aluno.usecase.teacher;

import com.uff.espaco_aluno.model.dto.teacher.TeacherDashboardDTO;
import com.uff.espaco_aluno.model.entity.Classroom;
import com.uff.espaco_aluno.model.entity.Discipline;
import com.uff.espaco_aluno.model.entity.StudentClassroom;
import com.uff.espaco_aluno.model.repository.ClassroomRepository;
import com.uff.espaco_aluno.model.repository.DisciplineRepository;
import com.uff.espaco_aluno.model.repository.StudentClassroomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetDashboardTeacherTest {

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private StudentClassroomRepository studentClassroomRepository;

    @InjectMocks
    private GetDashboardTeacher getDashboardTeacher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute_withValidTeacherId() {
        String teacherId = "id-do-professor";

        Discipline discipline = new Discipline();
        Classroom classroom = new Classroom();
        StudentClassroom studentClassroom = new StudentClassroom();
        studentClassroom.setScore(8.5);
        studentClassroom.setPresence(true);

        when(disciplineRepository.findByTeacherId(teacherId)).thenReturn(Arrays.asList(discipline));
        when(classroomRepository.findByTeacherId(teacherId)).thenReturn(Arrays.asList(classroom));
        when(studentClassroomRepository.findAllByClassrooms(Arrays.asList(classroom))).thenReturn(Arrays.asList(studentClassroom));
        when(studentClassroomRepository.countStudentsInClassrooms(Arrays.asList(classroom))).thenReturn(1);
        when(studentClassroomRepository.countApprovedStudents(Arrays.asList(classroom))).thenReturn(1);

        TeacherDashboardDTO result = getDashboardTeacher.execute(teacherId);

        assertNotNull(result);
        assertEquals(1, result.getTotalClasses());
        assertEquals(0, result.getConductedClasses());
        assertEquals(100.0, result.getAttendanceAverage());
        assertEquals(8.5, result.getClassAverage());
        assertEquals(1, result.getGradeDistribution().size());
        assertEquals(1, result.getTotalStudents());
        assertEquals(1, result.getApprovedStudents());
        assertEquals(100.0, result.getApprovalRate());
        assertEquals(1, result.getRemainingClasses());
    }

    @Test
    public void testCalculateAttendanceAverage_withNoAttendance() {
        Classroom classroom = new Classroom();
        when(studentClassroomRepository.findAllByClassrooms(Arrays.asList(classroom))).thenReturn(Arrays.asList());

        double result = getDashboardTeacher.calculateAttendanceAverage(Arrays.asList(classroom));

        assertEquals(0.0, result);
    }

    @Test
    public void testCalculateClassAverage_withMultipleScores() {
        Classroom classroom = new Classroom();
        StudentClassroom studentClassroom1 = new StudentClassroom();
        studentClassroom1.setScore(7.0);
        StudentClassroom studentClassroom2 = new StudentClassroom();
        studentClassroom2.setScore(9.0);
        when(studentClassroomRepository.findAllByClassrooms(Arrays.asList(classroom)))
                .thenReturn(Arrays.asList(studentClassroom1, studentClassroom2));

        double result = getDashboardTeacher.calculateClassAverage(Arrays.asList(classroom));

        assertEquals(8.0, result);
    }

    @Test
    public void testGetGradeDistribution_withMultipleGrades() {
        Classroom classroom = new Classroom();
        StudentClassroom studentClassroom1 = new StudentClassroom();
        studentClassroom1.setScore(9.5);
        StudentClassroom studentClassroom2 = new StudentClassroom();
        studentClassroom2.setScore(7.5);
        StudentClassroom studentClassroom3 = new StudentClassroom();
        studentClassroom3.setScore(4.5);
        when(studentClassroomRepository.findAllByClassrooms(Arrays.asList(classroom)))
                .thenReturn(Arrays.asList(studentClassroom1, studentClassroom2, studentClassroom3));

        Map<String, Long> result = getDashboardTeacher.getGradeDistribution(Arrays.asList(classroom));

        assertNotNull(result);
        assertEquals(1, result.get("Excelente (9-10)"));
        assertEquals(1, result.get("Bom (7-8.9)"));
        assertEquals(1, result.get("Insuficiente (<5)"));
    }
}
