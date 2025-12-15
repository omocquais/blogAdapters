package com.om.engine.application.adapters;

import com.om.engine.application.domain.Comment;
import com.om.engine.application.ports.out.CommentRepositoryPort;
import com.om.engine.application.services.TechnicalException;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CalciteCommentRepositoryPort implements CommentRepositoryPort {

    private final Path modelResourcePath;
    private final CalciteConnectionProvider calciteConnectionProvider;

    public CalciteCommentRepositoryPort(Path modelResourcePath, CalciteConnectionProvider calciteConnectionProvider) {
        this.modelResourcePath = modelResourcePath;
        this.calciteConnectionProvider = calciteConnectionProvider;
    }

    static final String SQL_SELECT_COMMENTS = """
                SELECT "id", "author_name", "author_email", "content", "comment_date", "article_id"
                FROM POSTGRES."comments"
            """;

    @Override
    public List<Comment> findAll() {
        var comments = new ArrayList<Comment>();

        try (
                var calciteConnection = calciteConnectionProvider.getConnection(modelResourcePath);
                var statement = calciteConnection.createStatement();
                var rs = statement.executeQuery(SQL_SELECT_COMMENTS)) {

            while (rs.next()) {
                comments.add(mapComment(rs));
            }

            return comments;
        } catch (SQLException e) {
            throw new TechnicalException("Error retrieving comments", e);
        }
    }

    private Comment mapComment(ResultSet resultSet) throws SQLException {

        Timestamp commentDate = resultSet.getTimestamp("comment_date");

        return new Comment(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("author_name"),
                resultSet.getString("author_email"),
                resultSet.getString("content"),
                commentDate != null ?
                        commentDate.toLocalDateTime() : null,
                UUID.fromString(resultSet.getString("article_id"))
        );
    }
}
