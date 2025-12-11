package com.om.engine.application.adapters;

import com.om.engine.application.domain.Author;
import com.om.engine.application.ports.out.AuthorRepositoryPort;
import com.om.engine.application.services.TechnicalException;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CalciteAuthorRepositoryPort implements AuthorRepositoryPort {

    private final Path modelResourcePath;
    private final CalciteConnectionProvider calciteConnectionProvider;

    private static final String SQL_SELECT_AUTHORS = """
            SELECT "id", "username", "email"
            FROM POSTGRES."authors"
            """;

    public CalciteAuthorRepositoryPort(Path modelResourcePath, CalciteConnectionProvider calciteConnectionProvider) {
        this.modelResourcePath = modelResourcePath;
        this.calciteConnectionProvider = calciteConnectionProvider;
    }

    @Override
    public List<Author> findAll() {

        List<Author> authors = new ArrayList<>();

        try (
                var calciteConnection = calciteConnectionProvider.getConnection(modelResourcePath);
                var statement = calciteConnection.createStatement();
                var rs = statement.executeQuery(SQL_SELECT_AUTHORS)) {

            while (rs.next()) {
                authors.add(mapAuthor(rs));
            }

        } catch (SQLException e) {
            throw new TechnicalException("Error retrieving authors", e);
        }

        return authors;
    }

    private @NonNull Author mapAuthor(ResultSet resultSet) throws SQLException {
        return new Author(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("username"),
                resultSet.getString("email")
        );
    }
}
