package com.uff.espaco_aluno.controller;

import com.uff.espaco_aluno.model.dto.CreateResponseDto;
import com.uff.espaco_aluno.model.dto.coordinator.CoordinatorCreateDto;
import com.uff.espaco_aluno.model.dto.coordinator.CoordinatorResponseDto;
import com.uff.espaco_aluno.service.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/coordinator")
public class CoordinatorController {

    @Autowired
    CoordinatorService service;

    @PostMapping("/create")
    private ResponseEntity<CreateResponseDto> createCoordinator(@RequestBody CoordinatorCreateDto entryDto) throws Exception {
        if (Objects.isNull(entryDto.idSchool())) {
            CreateResponseDto token = service.createCoordinator(entryDto, entryDto.schoolName());
            return ResponseEntity.created(URI.create("/login")).body(token);
        }
        CreateResponseDto token = service.createCoordinator(entryDto);
        return ResponseEntity.created(URI.create("/login")).body(token);
    }

    @GetMapping("/{id}")
    private ResponseEntity<CoordinatorResponseDto> getCoordinatorById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok().body(service.getCoordinatorById(id));
    }
}
