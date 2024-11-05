package com.uff.espaco_aluno.usecase.teacher;

import com.uff.espaco_aluno.model.entity.Student;
import com.uff.espaco_aluno.model.entity.StudentClassroom;
import com.uff.espaco_aluno.model.entity.StudentDiscipline;
import com.uff.espaco_aluno.model.repository.DisciplineRepository;
import com.uff.espaco_aluno.model.repository.StudentClassroomRepository;
import com.uff.espaco_aluno.model.repository.StudentDisciplineRepository;
import com.uff.espaco_aluno.model.repository.StudentRepository;
import com.uff.espaco_aluno.utils.enums.StudentStatusDIsciplineEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegisterStudentAbsenceUseCase {

    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentDisciplineRepository studentDisciplineRepository;
    private final StudentClassroomRepository studentClassroomRepository;

    @Autowired
    public RegisterStudentAbsenceUseCase(StudentRepository studentRepository,
                                         DisciplineRepository disciplineRepository,
                                         StudentDisciplineRepository studentDisciplineRepository,
                                         StudentClassroomRepository studentClassroomRepository) {
        this.studentRepository = studentRepository;
        this.disciplineRepository = disciplineRepository;
        this.studentDisciplineRepository = studentDisciplineRepository;
        this.studentClassroomRepository = studentClassroomRepository;
    }

    public void execute(UUID studentId, String classroomId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        StudentClassroom studentClassroom = studentClassroomRepository.findByStudentIdAndClassroomId(studentId, classroomId)
                .orElseThrow(() -> new EntityNotFoundException("Student is not enrolled in this classroom"));

        studentClassroom.setPresence(false);
        studentClassroomRepository.save(studentClassroom);

        long absencesCount = studentClassroomRepository.countAbsencesByStudentAndDiscipline(studentId, studentClassroom.getDisciplineId());

        if (absencesCount > 6) {
            StudentDiscipline studentDiscipline = studentDisciplineRepository.findByStudentIdAndDisciplineId(studentId, studentClassroom.getDisciplineId())
                    .orElseThrow(() -> new EntityNotFoundException("Enrollment not found for this student in the discipline"));
            studentDiscipline.setStatus(StudentStatusDIsciplineEnum.FAILED_DUE_TO_ABSENCE);
            studentDisciplineRepository.save(studentDiscipline);
        }
    }
}

