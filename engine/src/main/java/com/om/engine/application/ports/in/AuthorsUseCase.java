package com.om.engine.application.ports.in;

import com.om.engine.application.domain.Author;

import java.util.List;

public interface AuthorsUseCase {
    /***
     * Get all authors
     * @return list of authors
     */
    List<Author> getAuthors();
}
