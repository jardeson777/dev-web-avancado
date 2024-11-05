package com.uff.espaco_aluno.usecase.coordinator;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.model.dto.LoginDto;
import com.uff.espaco_aluno.model.dto.ResponseTokenDto;
import com.uff.espaco_aluno.model.repository.CoordinatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetCoordinatorByLogin {

    @Autowired
    private CoordinatorRepository repository;

    public ResponseTokenDto execute(LoginDto dto) throws Exception {
        return repository.findByEmailAndPassword(dto.email(), dto.password())
                .map(coordinator -> ResponseTokenDto.newToken(coordinator.getId() + ":" + coordinator.getRole().name()))
                .orElseThrow(InvalidLoginException::new);
    }
}
