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
import com.uff.espaco_aluno.usecase.school.GetSchoolById;
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
    GetSchoolById getSchoolById;

    public CreateResponseDto createTeacher(TeacherCreateDto dto) throws Exception {

        if (dto == null ||
                dto.email() == null ||
                dto.email().isBlank() ||
                dto.schoolId() == null) {
            throw new NullInfoException();
        }

        Teacher teacher = dto.newTeacher();
        teacher.setIsActive(true);
        School school = getSchoolById.execute(dto.schoolId());

        teacher.setSchool(school);
        Teacher savedTeacher = repository.save(teacher);

        return CreateResponseDto.newUser(savedTeacher.getId());
    }

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
