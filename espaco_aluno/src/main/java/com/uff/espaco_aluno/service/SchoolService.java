package com.uff.espaco_aluno.service;

import com.uff.espaco_aluno.exception.InvalidLoginException;
import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository repository;

    public School createSchool(String name) {
        School school = new School();
        school.setName(name);

        return repository.save(school);
    }

    public School getSchoolById(UUID id) throws Exception {
       return repository.findById(id).orElseThrow(InvalidLoginException::new);
    }
}
