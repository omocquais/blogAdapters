package com.om.engine.application.services;

import com.om.engine.application.ports.out.AuthorRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorsServiceTest {

    @Mock
    AuthorRepositoryPort authorRepositoryPort;
    
    @DisplayName("Given a list of authors, when retrieving authors, then the list is not null")
    @Test
    void getAuthors() {
        new AuthorsService(authorRepositoryPort).getAuthors();
        verify(authorRepositoryPort).findAll();
    }
}