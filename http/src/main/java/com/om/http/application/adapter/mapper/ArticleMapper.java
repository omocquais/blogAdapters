package com.om.http.application.adapter.mapper;

import com.om.engine.application.domain.Article;
import com.om.http.application.adapter.dto.ArticleDTO;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public ArticleDTO toDTO(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }
        return new ArticleDTO(article.id(), article.title(), article.content(), article.publishDate(),
                article.isPublished(), article.authorId());
    }
}
