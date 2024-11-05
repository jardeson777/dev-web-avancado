package com.uff.espaco_aluno.usecase.school;

import com.uff.espaco_aluno.exception.SchoolNotFoundException;
import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.model.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetSchoolById {

    @Autowired
    private SchoolRepository repository;

    public School execute(UUID id) throws SchoolNotFoundException {
        return repository.findById(id).orElseThrow(SchoolNotFoundException::new);
    }
}
