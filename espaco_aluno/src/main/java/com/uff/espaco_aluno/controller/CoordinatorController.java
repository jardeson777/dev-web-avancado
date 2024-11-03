package com.uff.espaco_aluno.controller;

import com.uff.espaco_aluno.model.dto.CreateResponseDto;
import com.uff.espaco_aluno.model.dto.coordinator.CoordinatorCreateDto;
import com.uff.espaco_aluno.model.dto.UserResponseDto;
import com.uff.espaco_aluno.model.dto.student.StudentCreateDto;
import com.uff.espaco_aluno.model.dto.teacher.TeacherCreateDto;
import com.uff.espaco_aluno.service.CoordinatorService;
import com.uff.espaco_aluno.service.StudentService;
import com.uff.espaco_aluno.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/coordinator")
public class CoordinatorController {

    @Autowired
    CoordinatorService service;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;

    @PostMapping("/create")
    private ResponseEntity<CreateResponseDto> createCoordinator(@RequestBody CoordinatorCreateDto entryDto) throws Exception {
        if (Objects.isNull(entryDto.idSchool())) {
            CreateResponseDto token = service.createCoordinator(entryDto, entryDto.schoolName());
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        }
        CreateResponseDto token = service.createCoordinator(entryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserResponseDto> getCoordinatorById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok().body(service.getCoordinatorById(id));
    }

    @PostMapping("/create/teacher")
    private ResponseEntity<CreateResponseDto> createTeacher(@RequestBody TeacherCreateDto teacherCreateDto) throws Exception {
        CreateResponseDto responseDto = teacherService.createTeacher(teacherCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/create/student")
    private ResponseEntity<CreateResponseDto> createStudent(@RequestBody StudentCreateDto teacherCreateDto) throws Exception {
        CreateResponseDto responseDto = studentService.createStudent(teacherCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
