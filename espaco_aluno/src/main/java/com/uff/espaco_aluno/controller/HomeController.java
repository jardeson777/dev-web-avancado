package com.uff.espaco_aluno.controller;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.exception.InvalidUserException;
import com.uff.espaco_aluno.model.dto.CreateResponseDto;
import com.uff.espaco_aluno.model.dto.LoginDto;
import com.uff.espaco_aluno.model.dto.ResponseTokenDto;
import com.uff.espaco_aluno.model.dto.ValidateUserDto;
import com.uff.espaco_aluno.service.CoordinatorService;
import com.uff.espaco_aluno.service.TeacherService;
import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Autowired
    CoordinatorService coordinatorService;
    @Autowired
    TeacherService teacherService;

    @GetMapping("/")
    public String home() {
        return "Bem-vindo ao Espaço Aluno!";
    }

    @PostMapping("/login")
    private ResponseEntity<ResponseTokenDto> getByLogin(@RequestBody LoginDto dto) throws Exception {
        if (dto.role() == null) {
            throw new InvalidLoginException(ExceptionsEnum.UNREPORTED_ROLE);
        }

        return switch (dto.role()) {
            case COORDINATOR -> ResponseEntity.ok().body(coordinatorService.getCoordinatorByLogin(dto));
            case STUDENT -> ResponseEntity.ok(ResponseTokenDto.newToken("Student logic not yet implemented."));
            case TEACHER -> ResponseEntity.ok().body(teacherService.getTeacherByLogin(dto));
            default -> throw new InvalidLoginException();
        };
    }

    @PutMapping("/validate")
    private ResponseEntity<ResponseTokenDto> validateAccount(@RequestBody ValidateUserDto validateDto) throws Exception {
        return switch (validateDto.role()) {
            case TEACHER -> ResponseEntity.ok().body(teacherService.validateTeacher(validateDto));
            default -> throw new InvalidUserException();
        };
    }

}

