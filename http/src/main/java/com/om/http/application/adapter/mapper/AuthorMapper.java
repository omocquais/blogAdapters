package com.om.http.application.adapter.mapper;

import com.om.engine.application.domain.Author;
import com.om.http.application.adapter.dto.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorDTO toDTO(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        return new AuthorDTO(author.id(), author.username(), author.email());
    }
}
