package com.uff.espaco_aluno.usecase.school;

import com.uff.espaco_aluno.model.entity.School;
import com.uff.espaco_aluno.model.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateSchool {

    @Autowired
    private SchoolRepository repository;

    public School execute(String name) {
        School school = new School();
        school.setName(name);
        return repository.save(school);
    }
}
