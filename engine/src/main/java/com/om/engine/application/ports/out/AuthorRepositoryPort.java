package com.om.engine.application.ports.out;

import com.om.engine.application.domain.Author;

import java.util.List;

public interface AuthorRepositoryPort {

    List<Author> findAll();

}
