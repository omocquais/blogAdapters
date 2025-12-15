package com.om.mcp.config;

import com.om.engine.application.adapters.*;
import com.om.engine.application.ports.in.ArticlesUseCase;
import com.om.engine.application.ports.in.AuthorsUseCase;
import com.om.engine.application.ports.in.CommentsUseCase;
import com.om.engine.application.ports.out.ArticleRepositoryPort;
import com.om.engine.application.ports.out.AuthorRepositoryPort;
import com.om.engine.application.ports.out.CommentRepositoryPort;
import com.om.engine.application.services.ArticleService;
import com.om.engine.application.services.AuthorsService;
import com.om.engine.application.services.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class ApplicationConfig {

    public static final String BLOGDB_MODEL_JSON = "blogdb-model.json";

    @Bean
    public CalciteConnectionProvider calciteConnectionProvider() {
        return new CalciteConnectionProviderImpl();
    }

    @Bean
    public AuthorRepositoryPort authorRepositoryPort(@Value("${models.config.dir}") String configDir,
                                                     CalciteConnectionProvider calciteConnectionProvider) {
        return new CalciteAuthorRepositoryPort(Path.of(configDir, BLOGDB_MODEL_JSON),  calciteConnectionProvider);
    }

    @Bean
    public AuthorsUseCase authorsUseCase(AuthorRepositoryPort authorRepositoryPort) {
        return new AuthorsService(authorRepositoryPort);
    }

    @Bean
    public CommentRepositoryPort commentRepositoryPort(@Value("${models.config.dir}") String configDir,
                                                       CalciteConnectionProvider calciteConnectionProvider) {
        return new CalciteCommentRepositoryPort(Path.of(configDir, BLOGDB_MODEL_JSON),  calciteConnectionProvider);
    }

    @Bean
    public ArticleRepositoryPort articleRepositoryPort(@Value("${models.config.dir}") String configDir,
                                                       CalciteConnectionProvider calciteConnectionProvider) {
        return new CalciteArticleRepositoryPort(Path.of(configDir, BLOGDB_MODEL_JSON),  calciteConnectionProvider);
    }

    @Bean
    public ArticlesUseCase articlesUseCase(ArticleRepositoryPort articleRepositoryPort) {
        return new ArticleService(articleRepositoryPort);
    }

    @Bean
    public CommentsUseCase commentsUseCase(CommentRepositoryPort commentRepositoryPort) {
        return new CommentService(commentRepositoryPort);
    }
}
