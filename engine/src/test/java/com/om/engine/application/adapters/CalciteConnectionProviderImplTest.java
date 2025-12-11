package com.om.engine.application.adapters;

import org.apache.calcite.jdbc.CalciteConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalciteConnectionProviderImplTest {
    
    @DisplayName("Given an existing and valid modelResource, it should return a Connection")
    @Test
    void getConnection(@TempDir Path tempDir) throws IOException {

        try (var postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))
                .withUsername("admin")
                .withPassword("admin")
                .withDatabaseName("blogdb")
        ) {
            postgres.start();

            String formattedJson = """
                    {
                      "version": "1.0",
                      "defaultSchema": "POSTGRES",
                      "schemas": [
                        {
                          "type": "jdbc",
                          "name": "POSTGRES",
                          "jdbcDriver": "org.postgresql.Driver",
                          "jdbcUrl": "%s",
                          "jdbcUser": "admin",
                          "jdbcPassword": "admin"
                        }
                      ]
                    }
                    """.formatted(postgres.getJdbcUrl());

            File modelFile = new File(tempDir.toFile(), "blogdb-model.json");
            Path modelResourcePath = Files.writeString(modelFile.toPath(), formattedJson, StandardCharsets.UTF_8);

            CalciteConnectionProviderImpl calciteConnectionProvider = new CalciteConnectionProviderImpl();
            CalciteConnection connection = calciteConnectionProvider.getConnection(modelResourcePath);
            assertThat(connection).isNotNull();

            postgres.stop();
        }
    }

    @Nested
    class IncorrectModelResourcePathTests {

        @DisplayName("Given a null value for the modelResource parameter, it should throw an " +
                "IllegalArgumentException")
        @Test
        void resolveModelPath() {
            CalciteConnectionProviderImpl calciteConnectionProvider = new CalciteConnectionProviderImpl();
            assertThatThrownBy(() -> calciteConnectionProvider.getConnection(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("modelResourcePath cannot be null");
        }

        @DisplayName("Given an incorrect path for the modelResource, it should throw an IllegalArgumentException")
        @Test
        void invalidModelResourcePath() {

            Path invalidModelResourcePath = Path.of("xxx.json");
            CalciteConnectionProviderImpl calciteConnectionProvider = new CalciteConnectionProviderImpl();
            assertThatThrownBy(() -> calciteConnectionProvider.getConnection(invalidModelResourcePath))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("modelResourcePath does not exist: xxx.json");
        }
    }


}