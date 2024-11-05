package com.uff.espaco_aluno.usecase.coordinator;

import com.uff.espaco_aluno.exception.InvalidUserException;
import com.uff.espaco_aluno.model.dto.UserResponseDto;
import com.uff.espaco_aluno.model.entity.Coordinator;
import com.uff.espaco_aluno.model.repository.CoordinatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetCoordinatorById {

    @Autowired
    private CoordinatorRepository repository;

    public UserResponseDto execute(UUID id) throws InvalidUserException {
        Coordinator coordinator = repository.findById(id).orElseThrow(InvalidUserException::new);
        return UserResponseDto.mapCoordinatorToUserResponseDTO(coordinator);
    }
}