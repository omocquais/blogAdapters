package com.om.engine.application.services;

import com.om.engine.application.domain.Article;
import com.om.engine.application.ports.out.ArticleRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    ArticleRepositoryPort articleRepositoryPort;

    @DisplayName("Given a list of articles, when retrieving articles, then the list is not null")
    @Test
    void getArticles() {
        List<Article> articles = new ArticleService(articleRepositoryPort).getArticles();
        assertNotNull(articles);
        verify(articleRepositoryPort).findAll();
    }


    @DisplayName("Given a list of articles, when retrieving published articles, then the list is not null")
    @Test
    void getUnpublishedArticles() {
        List<Article> articles = new ArticleService(articleRepositoryPort).getUnpublishedArticles();
        assertNotNull(articles);
        verify(articleRepositoryPort).findUnpublishedArticles();
    }

    @DisplayName("Given a list of articles, when retrieving unpublished articles, then the list is not null")
    @Test
    void getPublishedArticles() {
        List<Article> articles = new ArticleService(articleRepositoryPort).getPublishedArticles();
        assertNotNull(articles);
        verify(articleRepositoryPort).findPublishedArticles();
    }
}