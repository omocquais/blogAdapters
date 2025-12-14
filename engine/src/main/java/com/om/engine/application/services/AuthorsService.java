package com.om.engine.application.services;

import com.om.engine.application.domain.Author;
import com.om.engine.application.ports.in.AuthorsUseCase;
import com.om.engine.application.ports.out.AuthorRepositoryPort;

import java.util.List;

public class AuthorsService implements AuthorsUseCase {

    private final AuthorRepositoryPort authorRepositoryPort;

    public AuthorsService(AuthorRepositoryPort authorRepositoryPort) {
        this.authorRepositoryPort = authorRepositoryPort;
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepositoryPort.findAll();
    }

}
