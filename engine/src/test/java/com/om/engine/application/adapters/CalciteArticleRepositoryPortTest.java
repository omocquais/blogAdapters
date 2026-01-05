package com.om.engine.application.adapters;

import com.om.engine.application.domain.Article;
import com.om.engine.application.services.TechnicalException;
import org.apache.calcite.jdbc.CalciteConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.om.engine.application.adapters.CalciteArticleRepositoryPort.SQL_SELECT_PUBLISHED_ARTICLES;
import static com.om.engine.application.adapters.CalciteArticleRepositoryPort.SQL_SELECT_UNPUBLISHED_ARTICLES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalciteArticleRepositoryPortTest {

    @Mock
    CalciteConnectionProvider calciteConnectionProvider;

    @Mock
    private CalciteConnection connection;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @DisplayName("Given a list of articles, when retrieving articles, then the list is not null")
    @Test
    void findAllArticles(@TempDir Path tempDirPath) throws SQLException {

        //Given
        when(calciteConnectionProvider.getConnection(tempDirPath)).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);

        String sqlQuery = """
                    SELECT "id", "title", "content", "publish_date", "is_published", "author_id"
                    FROM POSTGRES."articles"
                """;
        when(statement.executeQuery(sqlQuery)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("id")).thenReturn("f1486558-dd9e-4f4b-9376-408f6d94c18b", "2e8d0e54-0e90-42d0-aeca-2e1e20865d08");
        when(resultSet.getString("title")).thenReturn("Article 1", "Article 2");
        when(resultSet.getString("content")).thenReturn("Article 1 content", "Article 2 content");
        Timestamp timestamp1 = Timestamp.valueOf(LocalDateTime.of(2024, 1, 15, 10,
                30, 45));
        Timestamp timestamp2 = null;
        when(resultSet.getTimestamp("publish_date")).thenReturn(timestamp1, timestamp2);
        when(resultSet.getBoolean("is_published")).thenReturn(true, false);
        when(resultSet.getString("author_id")).thenReturn("38415349-1189-47d8-95f8-3e32a9cf7993", "8f7488c4-a1ba" +
                "-4640-818a-0e3f09f42ac9");

        // When
        List<Article> articles = new CalciteArticleRepositoryPort(tempDirPath, calciteConnectionProvider).findAll();

        // Then
        assertThat(articles).isNotNull();

        verify(calciteConnectionProvider).getConnection(tempDirPath);

        //Then
        assertThat(articles).isNotNull().isNotEmpty().hasSize(2)
                .containsExactly(
                        new Article(UUID.fromString("f1486558-dd9e-4f4b-9376-408f6d94c18b"), "Article 1", "Article 1 " +
                                "content", timestamp1.toLocalDateTime(), true, UUID.fromString("38415349-1189-47d8" +
                                "-95f8-3e32a9cf7993")),
                        new Article(UUID.fromString("2e8d0e54-0e90-42d0-aeca-2e1e20865d08"), "Article 2", "Article 2 " +
                                "content", null, false, UUID.fromString("8f7488c4-a1ba" +
                                "-4640-818a-0e3f09f42ac9"))
                );
    }


    @DisplayName("Given a list of articles, when they are retrieved, the list is not null and all articles have the isPublished attribute set to true.")
    @Test
    void findPublishedArticles(@TempDir Path tempDirPath) throws SQLException {
        //Given
        when(calciteConnectionProvider.getConnection(tempDirPath)).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(SQL_SELECT_PUBLISHED_ARTICLES)).thenReturn(resultSet);
        
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("id")).thenReturn("f1486558-dd9e-4f4b-9376-408f6d94c18b", "2e8d0e54-0e90-42d0-aeca-2e1e20865d08");
        when(resultSet.getString("title")).thenReturn("Article 1", "Article 2");
        when(resultSet.getString("content")).thenReturn("Article 1 content", "Article 2 content");

        Timestamp timestamp1 = Timestamp.valueOf(LocalDateTime.of(2024, 1, 15, 10,
                30, 45));
        Timestamp timestamp2 = Timestamp.valueOf(LocalDateTime.of(2025, 2, 20, 15,
                35, 55));

        when(resultSet.getTimestamp("publish_date")).thenReturn(timestamp1, timestamp2);
        when(resultSet.getBoolean("is_published")).thenReturn(true, true);
        when(resultSet.getString("author_id")).thenReturn("38415349-1189-47d8-95f8-3e32a9cf7993", "8f7488c4-a1ba" +
                "-4640-818a-0e3f09f42ac9");

        // When
        List<Article> articles = new CalciteArticleRepositoryPort(tempDirPath, calciteConnectionProvider).findPublishedArticles();

        // Then
        assertThat(articles).isNotNull().isNotEmpty().hasSize(2)
                .containsExactly(
                        new Article(UUID.fromString("f1486558-dd9e-4f4b-9376-408f6d94c18b"), "Article 1", "Article 1 " +
                                "content", timestamp1.toLocalDateTime(), true, UUID.fromString("38415349-1189-47d8" +
                                "-95f8-3e32a9cf7993")),
                        new Article(UUID.fromString("2e8d0e54-0e90-42d0-aeca-2e1e20865d08"), "Article 2", "Article 2 " +
                                "content", timestamp2.toLocalDateTime(), true, UUID.fromString("8f7488c4-a1ba" +
                                "-4640-818a-0e3f09f42ac9"))
                );
    }

    @DisplayName("Given a list of articles, when they are retrieved, the list is not null and all articles have the " +
            "isPublished attribute set to false.")
    @Test
    void findUnpublishedArticles(@TempDir Path tempDirPath) throws SQLException {
        //Given
        when(calciteConnectionProvider.getConnection(tempDirPath)).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(SQL_SELECT_UNPUBLISHED_ARTICLES)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("id")).thenReturn("f1486558-dd9e-4f4b-9376-408f6d94c18b", "2e8d0e54-0e90-42d0-aeca-2e1e20865d08");
        when(resultSet.getString("title")).thenReturn("Article 1", "Article 2");
        when(resultSet.getString("content")).thenReturn("Article 1 content", "Article 2 content");

        Timestamp nullTimestamp = null;
        when(resultSet.getTimestamp("publish_date")).thenReturn(nullTimestamp, nullTimestamp);
        when(resultSet.getBoolean("is_published")).thenReturn(false, false);
        when(resultSet.getString("author_id")).thenReturn("38415349-1189-47d8-95f8-3e32a9cf7993", "8f7488c4-a1ba" +
                "-4640-818a-0e3f09f42ac9");

        // When
        List<Article> articles = new CalciteArticleRepositoryPort(tempDirPath, calciteConnectionProvider).findUnpublishedArticles();

        // Then
        assertThat(articles).isNotNull().isNotEmpty().hasSize(2)
                .containsExactly(
                        new Article(UUID.fromString("f1486558-dd9e-4f4b-9376-408f6d94c18b"), "Article 1", "Article 1 " +
                                "content", null, false, UUID.fromString("38415349-1189-47d8" +
                                "-95f8-3e32a9cf7993")),
                        new Article(UUID.fromString("2e8d0e54-0e90-42d0-aeca-2e1e20865d08"), "Article 2", "Article 2 " +
                                "content", null, false, UUID.fromString("8f7488c4-a1ba" +
                                "-4640-818a-0e3f09f42ac9"))
                );
    }
    
    @DisplayName("When retrieving a list of articles, a SQLException occurs, which results in a TechnicalException being thrown.")
    @Test
    void findAllArticlesWithSQLException(@TempDir Path tempDirPath) throws SQLException {
        //Given
        when(calciteConnectionProvider.getConnection(tempDirPath)).thenThrow(SQLException.class);

        // When
        CalciteArticleRepositoryPort articleRepositoryPort = new CalciteArticleRepositoryPort(tempDirPath, calciteConnectionProvider);
        assertThatThrownBy(articleRepositoryPort::findAll).isInstanceOf(TechnicalException.class).hasMessageContaining("Error retrieving articles");
    }

}