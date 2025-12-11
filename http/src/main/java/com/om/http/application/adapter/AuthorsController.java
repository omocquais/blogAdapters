package com.om.http.application.adapter;

import com.om.engine.application.ports.in.AuthorsUseCase;
import com.om.http.application.adapter.dto.AuthorDTO;
import com.om.http.application.adapter.mapper.AuthorMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorsController {

    private final AuthorsUseCase authorsUseCase;
    private final AuthorMapper authorMapper;

    public AuthorsController(AuthorsUseCase authorsUseCase, AuthorMapper authorMapper) {
        this.authorsUseCase = authorsUseCase;
        this.authorMapper = authorMapper;
    }

    @GetMapping("/authors")
    public List<AuthorDTO> getAuthors() {
        return authorsUseCase.getAuthors().stream()
                .map(authorMapper::toDTO)
                .toList();
    }

}
