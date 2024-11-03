package com.uff.espaco_aluno.service;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.exception.InvalidUserException;
import com.uff.espaco_aluno.exception.NullInfoException;
import com.uff.espaco_aluno.model.dto.*;
import com.uff.espaco_aluno.model.dto.student.StudentCreateDto;
import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.model.entity.Student;
import com.uff.espaco_aluno.repository.StudentRepository;
import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    StudentRepository repository;
    @Autowired
    SchoolService schoolService;

    public CreateResponseDto createStudent(StudentCreateDto dto) throws Exception {

        if (dto == null ||
                dto.email() == null ||
                dto.email().isBlank() ||
                dto.schoolId() == null) {
            throw new NullInfoException();
        }

        Student student = dto.newStudent();
        student.setIsActive(true);
        School school = schoolService.getSchoolById(dto.schoolId());

        student.setSchool(school);
        Student savedStudent = repository.save(student);

        return CreateResponseDto.newUser(savedStudent.getId());
    }

    public ResponseTokenDto getStudentByLogin(LoginDto dto) throws Exception {
        Student student = repository.findByEmailAndPassword(dto.email(), dto.password()).orElseThrow(InvalidLoginException::new);
        if (Boolean.FALSE.equals(student.getIsActive())) {
            throw new InvalidLoginException(ExceptionsEnum.INACTIVE_USER);
        }

        String userInfo = student.getId() + ":" + student.getRole().name();

        return ResponseTokenDto.newToken(Base64.getEncoder().encodeToString(userInfo.getBytes()));
    }

    public UserResponseDto getStudentById(UUID id) throws InvalidUserException {
        Student student = repository.findById(id).orElseThrow(InvalidUserException::new);

        return UserResponseDto.mapStudentToUserResponseDTO(student);
    }
}
