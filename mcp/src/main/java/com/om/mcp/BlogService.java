package com.om.mcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.om.engine.application.domain.Author;
import com.om.engine.application.ports.in.AuthorsUseCase;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private final AuthorsUseCase authorsUseCase;

    public BlogService(AuthorsUseCase authorsUseCase) {
        this.authorsUseCase = authorsUseCase;
    }

    @Tool(name = "getAuthors", description = "Get the full list of authors contributing to this blog")
    public String getAuthors() {
        try {
            List<Author> authors = authorsUseCase.getAuthors().stream().toList();
            return new JsonMapper().writeValueAsString(authors);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}