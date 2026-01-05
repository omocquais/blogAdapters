package com.om.engine.application.adapters;

import com.om.engine.application.domain.Comment;
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

import static com.om.engine.application.adapters.CalciteCommentRepositoryPort.SQL_SELECT_COMMENTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalciteCommentRepositoryPortTest {

    @Mock
    CalciteConnectionProvider calciteConnectionProvider;

    @Mock
    private CalciteConnection connection;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @DisplayName("Given a list of comments, when retrieving comments, then the list is not null")
    @Test
    void findAllComments(@TempDir Path tempDirPath) throws SQLException {

        //Given
        when(calciteConnectionProvider.getConnection(tempDirPath)).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(SQL_SELECT_COMMENTS)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("id")).thenReturn("adbb310f-8bbe-45db-a4ce-85440b715c62", "4552f7f3-50d8-4e85-861c-aadf097db529");
        when(resultSet.getString("author_name")).thenReturn("Bob", "John");
        when(resultSet.getString("author_email")).thenReturn("bob@test.com", "john@test.com");
        when(resultSet.getString("content")).thenReturn("Comment 1 content", "Comment 2 content");

        Timestamp timestamp1 = Timestamp.valueOf(LocalDateTime.of(2024, 1, 15, 10,
                30, 45));
        Timestamp timestamp2 = Timestamp.valueOf(LocalDateTime.of(2025, 10, 20, 11,
                25, 41));
        when(resultSet.getTimestamp("comment_date")).thenReturn(timestamp1, timestamp2);
        when(resultSet.getString("article_id")).thenReturn("648c847b-8d84-4b2b-8fda-0b341fb1a65f", "a3361293-6d7d-4537-8c77-d4120e9ba01a");

        // When
        List<Comment> comments = new CalciteCommentRepositoryPort(tempDirPath, calciteConnectionProvider).findAll();

        // Then
        assertThat(comments).isNotNull();

        verify(calciteConnectionProvider).getConnection(tempDirPath);

        //Then
        assertThat(comments).isNotNull().isNotEmpty().hasSize(2)
                .containsExactly(
                        new Comment(UUID.fromString("adbb310f-8bbe-45db-a4ce-85440b715c62"), "Bob", "bob@test.com",
                                "Comment 1 content", timestamp1.toLocalDateTime(), UUID.fromString("648c847b-8d84-4b2b-8fda-0b341fb1a65f")),

                        new Comment(UUID.fromString("4552f7f3-50d8-4e85-861c-aadf097db529"), "John", "john@test.com",
                                "Comment 2 content", timestamp2.toLocalDateTime(), UUID.fromString("a3361293-6d7d-4537-8c77-d4120e9ba01a"))
                );
    }

    @DisplayName("When retrieving a list of comments, a SQLException occurs, which results in a TechnicalException being thrown.")
    @Test
    void findAllArticlesWithSQLException(@TempDir Path tempDirPath) throws SQLException {
        //Given
        when(calciteConnectionProvider.getConnection(tempDirPath)).thenThrow(SQLException.class);

        // When
        CalciteCommentRepositoryPort commentRepositoryPort = new CalciteCommentRepositoryPort(tempDirPath, calciteConnectionProvider);
        assertThatThrownBy(commentRepositoryPort::findAll).isInstanceOf(TechnicalException.class).hasMessageContaining("Error retrieving comments");
    }

}