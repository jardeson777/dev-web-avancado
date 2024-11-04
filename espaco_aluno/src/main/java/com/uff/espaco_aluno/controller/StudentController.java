package com.uff.espaco_aluno.controller;

import com.uff.espaco_aluno.model.dto.UserResponseDto;
import com.uff.espaco_aluno.model.dto.student.StudentDashboardDto;
import com.uff.espaco_aluno.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uff.espaco_aluno.usecase.student.GetDashboardStudent;

import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService service;

    @Autowired
    GetDashboardStudent getDashboardStudent;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok().body(service.getStudentById(id));
    }

    @GetMapping("/dashboard/{id}")
    public ResponseEntity<StudentDashboardDto> getDashboard(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok().body(getDashboardStudent.execute(id));
    }
}
