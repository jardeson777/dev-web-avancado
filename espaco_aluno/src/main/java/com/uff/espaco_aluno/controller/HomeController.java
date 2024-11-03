package com.uff.espaco_aluno.controller;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.exception.InvalidUserException;
import com.uff.espaco_aluno.model.dto.*;
import com.uff.espaco_aluno.model.dto.school.IdSchoolDto;
import com.uff.espaco_aluno.model.entity.Coordinator;
import com.uff.espaco_aluno.service.CoordinatorService;
import com.uff.espaco_aluno.service.SchoolService;
import com.uff.espaco_aluno.service.StudentService;
import com.uff.espaco_aluno.service.TeacherService;
import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class HomeController {

    @Autowired
    CoordinatorService coordinatorService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    SchoolService schoolService;

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
            case STUDENT -> ResponseEntity.ok(studentService.getStudentByLogin(dto));
            case TEACHER -> ResponseEntity.ok().body(teacherService.getTeacherByLogin(dto));
            default -> throw new InvalidLoginException();
        };
    }

    @GetMapping("/school/{idCoordinator}")
    private ResponseEntity<IdSchoolDto> getIdSchoolByIdCoodinator(@PathVariable UUID idCoordinator) throws Exception {
        UserResponseDto coordinator = coordinatorService.getCoordinatorById(idCoordinator);

        return ResponseEntity.ok().body(IdSchoolDto.newIdSchoolDto(coordinator.SchoolId()));
    }
}

