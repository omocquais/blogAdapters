package com.om.mcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.om.engine.application.domain.Article;
import com.om.engine.application.domain.Author;
import com.om.engine.application.domain.Comment;
import com.om.engine.application.ports.in.ArticlesUseCase;
import com.om.engine.application.ports.in.AuthorsUseCase;
import com.om.engine.application.ports.in.CommentsUseCase;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private final AuthorsUseCase authorsUseCase;
    private final ArticlesUseCase articlesUseCase;
    private final CommentsUseCase commentsUseCase;

    public BlogService(AuthorsUseCase authorsUseCase, ArticlesUseCase articlesUseCase, CommentsUseCase commentsUseCase) {
        this.authorsUseCase = authorsUseCase;
        this.articlesUseCase = articlesUseCase;
        this.commentsUseCase = commentsUseCase;
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

    @Tool(name = "getPublishedArticles", description = "Get the full list of articles published to this blog")
    public String getPublishedArticles() {
        try {
            List<Article> articles = articlesUseCase.getPublishedArticles().stream().toList();
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule());
            return jsonMapper.writeValueAsString(articles);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Tool(name = "getUnpublishedArticles", description = "Get the full list of articles unpublished to this blog")
    public String getUnpublishedArticles() {
        try {
            List<Article> articles = articlesUseCase.getUnpublishedArticles().stream().toList();
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule());
            return jsonMapper.writeValueAsString(articles);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Tool(name = "getComments", description = "Get the full list of comments to this blog")
    public String getComments() {
        try {
            List<Comment> articles = commentsUseCase.getComments().stream().toList();
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule());
            return jsonMapper.writeValueAsString(articles);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}