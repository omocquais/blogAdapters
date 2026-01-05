package com.om.engine.application.adapters;

import com.om.engine.application.domain.Author;
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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalciteAuthorRepositoryPortTest {

    @Mock
    CalciteConnectionProvider calciteConnectionProvider;

    @Mock
    private CalciteConnection connection;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @DisplayName("Given a list of authors, when retrieving authors, then the list is not null")
    @Test
    void findAll(@TempDir Path tempDirPath) throws SQLException {
        //Given
        when(calciteConnectionProvider.getConnection(tempDirPath)).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);

        String sqlQuery = """
                SELECT "id", "username", "email"
                FROM POSTGRES."authors"
                """;

        when(statement.executeQuery(sqlQuery)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("id")).thenReturn("40e6215d-b5c6-4896-987c-f30f3678f608", "6ecd8c99-4036-403d-bf84-cf8400f67836");
        when(resultSet.getString("username")).thenReturn("alice", "bob");
        when(resultSet.getString("email")).thenReturn("alice@example.com", "bob@example.com");

        //When
        List<Author> authors = new CalciteAuthorRepositoryPort(tempDirPath, calciteConnectionProvider).findAll();
        verify(calciteConnectionProvider).getConnection(tempDirPath);

        //Then
        assertThat(authors).isNotNull().isNotEmpty().hasSize(2)
                .containsExactly(
                        new Author(UUID.fromString("40e6215d-b5c6-4896-987c-f30f3678f608"), "alice", "alice@example.com"),
                        new Author(UUID.fromString("6ecd8c99-4036-403d-bf84-cf8400f67836"), "bob", "bob@example.com"));
    }

    @DisplayName("When retrieving a list of authors, a SQLException occurs, which results in a TechnicalException being thrown.")
    @Test
    void findAllArticlesWithSQLException(@TempDir Path tempDirPath) throws SQLException {
        //Given
        when(calciteConnectionProvider.getConnection(tempDirPath)).thenThrow(SQLException.class);

        // When
        CalciteAuthorRepositoryPort authorRepositoryPort = new CalciteAuthorRepositoryPort(tempDirPath, calciteConnectionProvider);
        assertThatThrownBy(authorRepositoryPort::findAll).isInstanceOf(TechnicalException.class).hasMessageContaining("Error retrieving authors");
    }

}