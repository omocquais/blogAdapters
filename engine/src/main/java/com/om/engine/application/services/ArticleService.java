package com.om.engine.application.services;

import com.om.engine.application.domain.Article;
import com.om.engine.application.ports.in.ArticlesUseCase;
import com.om.engine.application.ports.out.ArticleRepositoryPort;

import java.util.List;

public class ArticleService implements ArticlesUseCase {

    private final ArticleRepositoryPort articleRepositoryPort;

    public ArticleService(ArticleRepositoryPort articleRepositoryPort) {
        this.articleRepositoryPort = articleRepositoryPort;
    }

    @Override
    public List<Article> getArticles() {
        return articleRepositoryPort.findAll();
    }

    @Override
    public List<Article> getPublishedArticles() {
        return articleRepositoryPort.findPublishedArticles();
    }

    @Override
    public List<Article> getUnpublishedArticles() {
        return articleRepositoryPort.findUnpublishedArticles();
    }
}
