package com.om.http.application.adapter;

import com.om.engine.application.ports.in.ArticlesUseCase;
import com.om.http.application.adapter.dto.ArticleDTO;
import com.om.http.application.adapter.mapper.ArticleMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticlesController {

    private final ArticlesUseCase articlesUseCase;
    private final ArticleMapper articleMapper;

    public ArticlesController(ArticlesUseCase articlesUseCase, ArticleMapper articleMapper) {
        this.articlesUseCase = articlesUseCase;
        this.articleMapper = articleMapper;
    }

    @GetMapping("/articles")
    public List<ArticleDTO> getArticles() {
        return articlesUseCase.getArticles().stream()
                .map(articleMapper::toDTO)
                .toList();
    }
}
