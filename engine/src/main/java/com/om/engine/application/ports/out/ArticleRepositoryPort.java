package com.om.engine.application.ports.out;

import com.om.engine.application.domain.Article;

import java.util.List;

public interface ArticleRepositoryPort {
    List<Article> findAll();

    List<Article> findPublishedArticles();

    List<Article> findUnpublishedArticles();

}
