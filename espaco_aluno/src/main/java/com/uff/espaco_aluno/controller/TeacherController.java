package com.uff.espaco_aluno.controller;

import com.uff.espaco_aluno.model.dto.UserResponseDto;
import com.uff.espaco_aluno.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok().body(service.getTeacherById(id));
    }

}
