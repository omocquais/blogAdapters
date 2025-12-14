package com.om.engine.application.ports.in;

import com.om.engine.application.domain.Article;

import java.util.List;

public interface ArticlesUseCase {

    /***
     * Get all articles
     * @return list of articles
     */
    List<Article> getArticles();

    /***
     * Get all published articles
     * @return list of published articles
     */
    List<Article> getPublishedArticles();

    /***
     * Get all unpublished articles
     * @return list of unpublished articles
     */
    List<Article> getUnpublishedArticles();

}
