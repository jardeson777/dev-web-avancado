package com.uff.espaco_aluno.service;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.exception.InvalidUserException;
import com.uff.espaco_aluno.model.dto.LoginDto;
import com.uff.espaco_aluno.model.dto.coordinator.CoordinatorCreateDto;
import com.uff.espaco_aluno.model.dto.coordinator.CoordinatorResponseDto;
import com.uff.espaco_aluno.model.entity.Coordinator;
import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.repository.CoordinatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
public class CoordinatorService {

    @Autowired
    CoordinatorRepository repository;

    @Autowired
    SchoolService schoolService;

    public String createCoordinator(CoordinatorCreateDto dto) throws Exception {
        if (Objects.isNull(dto.email())) {
            throw new Exception();
        }

        School school = schoolService.getSchoolById(dto.idSchool());

        return saveCoordinator(dto, school).getRole().name();
    }

    public String createCoordinator(CoordinatorCreateDto dto, String schoolName) throws Exception {
        if (Objects.isNull(dto.email())) {
            throw new Exception();
        }

        if(Objects.isNull(schoolName) || schoolName.isBlank()) {
            throw new Exception();
        }

        School school = schoolService.createSchool(schoolName);

        return saveCoordinator(dto, school).getId().toString();
    }

    private Coordinator saveCoordinator(CoordinatorCreateDto dto, School school) {
        Coordinator coordinator = new Coordinator();
        coordinator.setName(dto.name());
        coordinator.setEmail(dto.email());
        coordinator.setPassword(dto.password());
        coordinator.setSchool(school);

        return repository.save(coordinator);
    }

    public CoordinatorResponseDto getCoordinatorById(UUID id) throws Exception {
        Coordinator coordinator = repository.findById(id).orElseThrow(InvalidUserException::new);

        return CoordinatorResponseDto.mapToCoordinatorResponseDTO(coordinator);
    }

    public String getCoordinatorByLogin(LoginDto dto) throws Exception {
        Coordinator coordinator = repository.findByEmailAndPassword(dto.email(), dto.password()).orElseThrow(InvalidLoginException::new);

        String userInfo = coordinator.getEmail() + ":" + coordinator.getRole().name();

        return Base64.getEncoder().encodeToString(userInfo.getBytes());
    }
}
