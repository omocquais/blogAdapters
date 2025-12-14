package com.om.http.application.adapter.mapper;

import com.om.engine.application.domain.Article;
import com.om.http.application.adapter.dto.ArticleDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleMapperTest {

    @DisplayName("Given an Article, when toDTO is called, then an ArticleDTO is returned")
    @Test
    void toDTO() {
        UUID uuid = UUID.randomUUID();

        Timestamp timestamp1 = Timestamp.valueOf(LocalDateTime.of(2024, 1, 15, 10,
                30, 45));

        Article article = new Article(uuid, "Article 2", "Article 2 " +
                "content", timestamp1.toLocalDateTime(), false, UUID.fromString("8f7488c4-a1ba" +
                "-4e6c-9e2d-5f4e3c6b7d89"));

        ArticleDTO articleDTO = new ArticleMapper().toDTO(article);

        assertThat(articleDTO).isNotNull();
        assertThat(articleDTO.id()).isEqualTo(uuid);
        assertThat(articleDTO.title()).isEqualTo("Article 2");
        assertThat(articleDTO.content()).isEqualTo("Article 2 content");
        assertThat(articleDTO.publishDate()).isEqualTo(timestamp1.toLocalDateTime());
        assertThat(articleDTO.isPublished()).isFalse();
        assertThat(articleDTO.authorId()).isEqualTo(UUID.fromString("8f7488c4-a1ba-4e6c-9e2d-5f4e3c6b7d89"));
        
    }

    @DisplayName("Given an invalid parameter, when toDTO is called, iy should throw an IllegalArgumentException")
    @Test
    void invalidAuthor() {
        AuthorMapper authorMapper = new AuthorMapper();
        assertThatThrownBy(() -> authorMapper.toDTO(null)).isInstanceOf(IllegalArgumentException.class);
    }

}