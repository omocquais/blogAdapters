package com.om.engine.application.adapters;

import com.om.engine.application.domain.Article;
import com.om.engine.application.ports.out.ArticleRepositoryPort;
import com.om.engine.application.services.TechnicalException;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CalciteArticleRepositoryPort implements ArticleRepositoryPort {

    private final Path modelResourcePath;
    private final CalciteConnectionProvider calciteConnectionProvider;

    public CalciteArticleRepositoryPort(Path modelResourcePath, CalciteConnectionProvider calciteConnectionProvider) {
        this.modelResourcePath = modelResourcePath;
        this.calciteConnectionProvider = calciteConnectionProvider;
    }

    private static final String SQL_SELECT_ARTICLES = """
                SELECT "id", "title", "content", "publish_date", "is_published", "author_id"
                FROM POSTGRES."articles"
            """;

    static final String SQL_SELECT_PUBLISHED_ARTICLES = """
                SELECT "id", "title", "content", "publish_date", "is_published", "author_id"
                FROM POSTGRES."articles"
                WHERE "is_published" = TRUE
            """;

    static final String SQL_SELECT_UNPUBLISHED_ARTICLES = """
                SELECT "id", "title", "content", "publish_date", "is_published", "author_id"
                FROM POSTGRES."articles"
                WHERE "is_published" = FALSE
            """;

    @Override
    public List<Article> findAll() {
        return getArticles(SQL_SELECT_ARTICLES);
    }

    @Override
    public List<Article> findPublishedArticles() {
        return getArticles(SQL_SELECT_PUBLISHED_ARTICLES);
    }

    @Override
    public List<Article> findUnpublishedArticles() {
        return getArticles(SQL_SELECT_UNPUBLISHED_ARTICLES);
    }

    /**
     * Generic method to retrieve articles based on the provided SQL query.
     * @param sql the SQL query to execute
     * @return a list of articles
     */
    private List<Article> getArticles(String sql) {

        var articles = new ArrayList<Article>();

        try (
                var calciteConnection = calciteConnectionProvider.getConnection(modelResourcePath);
                var statement = calciteConnection.createStatement();
                var rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                articles.add(mapArticle(rs));
            }

            return articles;
        } catch (SQLException e) {
            throw new TechnicalException("Error retrieving articles", e);
        }
    }

    private Article mapArticle(ResultSet resultSet) throws SQLException {
        Timestamp publishDate = resultSet.getTimestamp("publish_date");
        return new Article(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("title"),
                resultSet.getString("content"),
                publishDate != null ?
                        publishDate.toLocalDateTime() : null,
                resultSet.getBoolean("is_published"),
                UUID.fromString(resultSet.getString("author_id"))
        );
    }
}
