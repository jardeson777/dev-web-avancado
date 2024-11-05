package com.uff.espaco_aluno.model.repository;

import com.uff.espaco_aluno.model.entity.Classroom;
import com.uff.espaco_aluno.model.entity.StudentClassroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, UUID> {

    @Query("SELECT sc FROM student_classroom sc WHERE sc.student.id = :studentId")
    List<StudentClassroom> findAllByStudent_id(UUID studentId);

    @Query("SELECT sc FROM StudentClassroom sc WHERE sc.classroom.id IN :classroomIds")
    List<StudentClassroom> findAllByClassrooms(@Param("classroomIds") List<Classroom> classrooms);

    // Conta o número de alunos únicos em uma lista de aulas
    @Query("SELECT COUNT(DISTINCT sc.student.id) FROM StudentClassroom sc WHERE sc.classroom.id IN :classroomIds")
    int countStudentsInClassrooms(@Param("classroomIds") List<Classroom> classrooms);

    // Conta o número de alunos aprovados em uma lista de aulas
    @Query("SELECT COUNT(DISTINCT sc.student.id) FROM StudentClassroom sc WHERE sc.classroom.id IN :classroomIds AND sc.score >= 6.0")
    int countApprovedStudents(@Param("classroomIds") List<Classroom> classrooms);

    Optional<StudentClassroom> findByStudentIdAndClassroomId(UUID studentId, String classroomId);

    @Query("SELECT COUNT(sc) FROM StudentClassroom sc WHERE sc.studentId = :studentId AND sc.disciplineId = :disciplineId AND sc.presence = false")
    long countAbsencesByStudentAndDiscipline(@Param("studentId") UUID studentId, @Param("disciplineId") String disciplineId);

}
