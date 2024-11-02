package com.uff.espaco_aluno.service;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.exception.InvalidUserException;
import com.uff.espaco_aluno.exception.NullInfoException;
import com.uff.espaco_aluno.model.dto.*;
import com.uff.espaco_aluno.model.dto.teacher.TeacherCreateDto;
import com.uff.espaco_aluno.model.entity.Coordinator;
import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.model.entity.Teacher;
import com.uff.espaco_aluno.repository.TeacherRepository;
import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository repository;

    @Autowired
    SchoolService schoolService;

    public CreateResponseDto createTeacher(TeacherCreateDto dto) throws Exception {

        if (dto == null ||
                dto.email() == null ||
                dto.email().isBlank() ||
                dto.schoolId() == null) {
            throw new NullInfoException();
        }

        Teacher teacher = dto.newTeacher();
        teacher.setIsActive(false);
        School school = schoolService.getSchoolById(dto.schoolId());

        teacher.setSchool(school);
        Teacher savedTeacher = repository.save(teacher);

        return CreateResponseDto.newUser(savedTeacher.getId());
    }

    public ResponseTokenDto validateTeacher(ValidateUserDto dto) throws InvalidUserException {
        Teacher teacher = repository.findByEmail(dto.email()).orElseThrow(InvalidUserException::new);

        teacher.setPassword(dto.password());
        teacher.setName(dto.name());
        teacher.setIsActive(true);

        Teacher updatedTeacher = repository.save(teacher);

        String userInfo = updatedTeacher.getId() + ":" + updatedTeacher.getRole().name();
        return  ResponseTokenDto.newToken(Base64.getEncoder().encodeToString(userInfo.getBytes()));
    };

    public UserResponseDto getTeacherById(UUID id) throws Exception {
        Teacher teacher = repository.findById(id).orElseThrow(InvalidUserException::new);

        return UserResponseDto.mapTeacherToUserResponseDTO(teacher);
    }

    public ResponseTokenDto getTeacherByLogin(LoginDto dto) throws Exception {
        Teacher teacher = repository.findByEmailAndPassword(dto.email(), dto.password()).orElseThrow(InvalidLoginException::new);
        if (Boolean.FALSE.equals(teacher.getIsActive())) {
            throw new InvalidLoginException(ExceptionsEnum.INACTIVE_USER);
        }

        String userInfo = teacher.getId() + ":" + teacher.getRole().name();

        return ResponseTokenDto.newToken(Base64.getEncoder().encodeToString(userInfo.getBytes()));
    }

}
