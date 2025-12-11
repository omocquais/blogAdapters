package com.om.http.application.config;

import com.om.engine.application.adapters.CalciteAuthorRepositoryPort;
import com.om.engine.application.adapters.CalciteConnectionProvider;
import com.om.engine.application.adapters.CalciteConnectionProviderImpl;
import com.om.engine.application.ports.in.AuthorsUseCase;
import com.om.engine.application.ports.out.AuthorRepositoryPort;
import com.om.engine.application.services.AuthorsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class ApplicationConfig {

    @Bean
    public CalciteConnectionProvider calciteConnectionProvider() {
        return new CalciteConnectionProviderImpl();
    }

    @Bean
    public AuthorRepositoryPort authorRepositoryPort(@Value("${models.config.dir}") String configDir,
                                                     CalciteConnectionProvider calciteConnectionProvider) {
        return new CalciteAuthorRepositoryPort(Path.of(configDir, "blogdb-model.json"),  calciteConnectionProvider);
    }

    @Bean
    public AuthorsUseCase authorsUseCase(AuthorRepositoryPort authorRepositoryPort) {
        return new AuthorsService(authorRepositoryPort);
    }
}
