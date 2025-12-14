package com.om.http.application.adapter;

import com.om.engine.application.domain.Article;
import com.om.engine.application.ports.in.ArticlesUseCase;
import com.om.http.application.adapter.dto.ArticleDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ArticlesControllerTest {

    @Autowired
    MockMvcTester mockMvc;

    @MockitoBean
    ArticlesUseCase articlesUseCase;

    @DisplayName("Given a list of articles, when retrieving articles, then the list is not null")
    @Test
    void getArticles() {

        UUID authorId1 = UUID.randomUUID();
        UUID authorId2 = UUID.randomUUID();

        LocalDateTime publishDate1 = LocalDateTime.of(2024, 1,
                1, 10, 0);
        Article article1 = new Article(UUID.randomUUID(), "article 1", "Article content 1", publishDate1, true, authorId1);

        LocalDateTime publishDate2 = LocalDateTime.of(2025, 2,
                10, 15, 10);
        Article article2 = new Article(UUID.randomUUID(), "article 2", "Article content 2", publishDate2, true, authorId2);

        List<Article> articleList = List.of(article1, article2);

        when(articlesUseCase.getArticles()).thenReturn(articleList);

        assertThat(mockMvc.get().uri("/articles")
                .accept(MediaType.APPLICATION_JSON))
                .hasStatusOk()
                .bodyJson()
                .convertTo(ArticleDTO[].class)
                .satisfies(articles -> {

                    assertThat(articles).isNotEmpty().hasSize(2);

                    assertThat(articles[0].id()).isNotNull().isInstanceOf(UUID.class);
                    assertThat(articles[0].title()).isNotEmpty().isEqualTo("article 1");
                    assertThat(articles[0].content()).isNotEmpty().isEqualTo("Article content 1");
                    assertThat(articles[0].publishDate()).isNotNull().isEqualTo(publishDate1);
                    assertThat(articles[0].isPublished()).isTrue();
                    assertThat(articles[0].authorId()).isNotNull().isEqualTo(authorId1);

                    assertThat(articles[1].id()).isNotNull().isInstanceOf(UUID.class);
                    assertThat(articles[1].title()).isNotEmpty().isEqualTo("article 2");
                    assertThat(articles[1].content()).isNotEmpty().isEqualTo("Article content 2");
                    assertThat(articles[1].publishDate()).isNotNull().isEqualTo(publishDate2);
                    assertThat(articles[1].isPublished()).isTrue();
                    assertThat(articles[1].authorId()).isNotNull().isEqualTo(authorId2);


                });

        verify(articlesUseCase).getArticles();

    }
}