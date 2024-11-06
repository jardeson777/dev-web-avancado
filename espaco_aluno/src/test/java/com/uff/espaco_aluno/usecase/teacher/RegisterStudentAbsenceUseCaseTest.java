package com.uff.espaco_aluno.usecase.teacher;

import com.uff.espaco_aluno.model.entity.Student;
import com.uff.espaco_aluno.model.entity.StudentClassroom;
import com.uff.espaco_aluno.model.entity.StudentDiscipline;
import com.uff.espaco_aluno.model.repository.StudentClassroomRepository;
import com.uff.espaco_aluno.model.repository.StudentDisciplineRepository;
import com.uff.espaco_aluno.model.repository.StudentRepository;
import com.uff.espaco_aluno.utils.enums.StudentStatusDIsciplineEnum;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterStudentAbsenceUseCaseTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentClassroomRepository studentClassroomRepository;

    @Mock
    private StudentDisciplineRepository studentDisciplineRepository;

    @InjectMocks
    private RegisterStudentAbsenceUseCase registerStudentAbsenceUseCase;

    private UUID studentId;
    private String classroomId;

    @BeforeEach
    public void setUp() {
        studentId = UUID.randomUUID();
        classroomId = "Testes unitários";
    }

    @Test
    public void testExecute_withValidData() {
        Student student = new Student();
        StudentClassroom studentClassroom = new StudentClassroom();
        studentClassroom.setPresence(true);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        when(studentClassroomRepository.findByStudentIdAndClassroomId(studentId, classroomId))
                .thenReturn(Optional.of(studentClassroom));

        when(studentClassroomRepository.countAbsencesByStudentAndDiscipline(studentId, studentClassroom.getDisciplineId()))
                .thenReturn(7L);

        StudentDiscipline studentDiscipline = new StudentDiscipline();
        studentDiscipline.setStatus(StudentStatusDIsciplineEnum.APPROVED);
        when(studentDisciplineRepository.findByStudentIdAndDisciplineId(studentId, studentClassroom.getDisciplineId()))
                .thenReturn(Optional.of(studentDiscipline));

        registerStudentAbsenceUseCase.execute(studentId, classroomId);

        verify(studentClassroomRepository).save(studentClassroom);
        verify(studentDisciplineRepository).save(any(StudentDiscipline.class));
    }

    @Test
    public void testExecute_withStudentFailedDueToAbsence() {
        Student student = new Student();
        UUID studentId = UUID.randomUUID();
        String classroomId = "classroomId";

        StudentClassroom studentClassroom = new StudentClassroom();
        studentClassroom.setPresence(true);
        studentClassroom.setDisciplineId("disciplineId");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        when(studentClassroomRepository.findByStudentIdAndClassroomId(studentId, classroomId))
                .thenReturn(Optional.of(studentClassroom));

        when(studentClassroomRepository.countAbsencesByStudentAndDiscipline(studentId, studentClassroom.getDisciplineId()))
                .thenReturn(7L);

        StudentDiscipline studentDiscipline = new StudentDiscipline();
        studentDiscipline.setStatus(StudentStatusDIsciplineEnum.APPROVED);

        when(studentDisciplineRepository.findByStudentIdAndDisciplineId(studentId, studentClassroom.getDisciplineId()))
                .thenReturn(Optional.of(studentDiscipline));

        registerStudentAbsenceUseCase.execute(studentId, classroomId);

        verify(studentClassroomRepository).save(studentClassroom);
        verify(studentDisciplineRepository).save(any(StudentDiscipline.class));

        assertEquals(StudentStatusDIsciplineEnum.FAILED_DUE_TO_ABSENCE, studentDiscipline.getStatus());
    }



    @Test
    public void testExecute_withStudentNotFound() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> registerStudentAbsenceUseCase.execute(studentId, classroomId));
    }

    @Test
    public void testExecute_withStudentNotEnrolledInClassroom() {
        Student student = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentClassroomRepository.findByStudentIdAndClassroomId(studentId, classroomId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> registerStudentAbsenceUseCase.execute(studentId, classroomId));
    }
}