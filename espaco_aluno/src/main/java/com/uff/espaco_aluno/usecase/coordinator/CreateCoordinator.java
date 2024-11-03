package com.uff.espaco_aluno.usecase.coordinator;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.exception.NullInfoException;
import com.uff.espaco_aluno.exception.SchoolNotFoundException;
import com.uff.espaco_aluno.model.dto.CreateResponseDto;
import com.uff.espaco_aluno.model.dto.coordinator.CoordinatorCreateDto;
import com.uff.espaco_aluno.model.entity.Coordinator;
import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.repository.CoordinatorRepository;
import com.uff.espaco_aluno.usecase.school.CreateSchool;
import com.uff.espaco_aluno.usecase.school.GetSchoolById;
import com.uff.espaco_aluno.utils.enums.ExceptionsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateCoordinator {

    @Autowired
    private CoordinatorRepository repository;

    @Autowired
    private GetSchoolById getSchoolById;

    @Autowired
    private CreateSchool createSchool;

    public CreateResponseDto execute(CoordinatorCreateDto dto) throws SchoolNotFoundException, InvalidLoginException, NullInfoException {
        validate(dto);
        School school = getSchoolById.execute(dto.idSchool());
        Coordinator coordinator = buildCoordinator(dto, school);
        Coordinator savedCoordinator = repository.save(coordinator);
        return CreateResponseDto.newUser(savedCoordinator.getId());
    }

    public CreateResponseDto execute(CoordinatorCreateDto dto, String schoolName) throws Exception {
        validate(dto, schoolName);
        School school = createSchool.execute(schoolName);
        Coordinator coordinator = buildCoordinator(dto, school);
        Coordinator savedCoordinator = repository.save(coordinator);
        return CreateResponseDto.newUser(savedCoordinator.getId());
    }

    private void validate(CoordinatorCreateDto dto) throws InvalidLoginException, NullInfoException {
        if (Objects.isNull(dto.email()) || dto.email().isBlank() || Objects.isNull(dto.password()) || dto.password().isBlank()) {
            throw new InvalidLoginException(ExceptionsEnum.EMAIL_PASSWORD_INVALID);
        }

        if (Objects.isNull(dto.idSchool())) {
            throw new NullInfoException();
        }
    }

    private void validate(CoordinatorCreateDto dto, String schoolName) throws InvalidLoginException, NullInfoException {
        if (Objects.isNull(dto.email()) || dto.email().isBlank() || Objects.isNull(dto.password()) || dto.password().isBlank()) {
            throw new InvalidLoginException(ExceptionsEnum.EMAIL_PASSWORD_INVALID);
        }

        if (Objects.isNull(schoolName) || schoolName.isBlank()) {
            throw new NullInfoException();
        }
    }

    private Coordinator buildCoordinator(CoordinatorCreateDto dto, School school) {
        Coordinator coordinator = new Coordinator();
        coordinator.setName(dto.name());
        coordinator.setEmail(dto.email());
        coordinator.setPassword(dto.password());
        coordinator.setSchoolId(school.getId());
        coordinator.setSchool(school);
        return coordinator;
    }
}