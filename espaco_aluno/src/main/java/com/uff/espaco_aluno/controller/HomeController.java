package com.uff.espaco_aluno.controller;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.model.dto.LoginDto;
import com.uff.espaco_aluno.model.dto.ResponseTokenDto;
import com.uff.espaco_aluno.service.CoordinatorService;
import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    CoordinatorService coordinatorService;
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
            case TEACHER -> ResponseEntity.ok(ResponseTokenDto.newToken("Teacher logic not yet implemented."));
            default -> throw new InvalidLoginException();
        };
    }

}

