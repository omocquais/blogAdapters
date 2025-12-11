package com.om.http.application.adapter;

import com.om.engine.application.domain.Author;
import com.om.engine.application.ports.in.AuthorsUseCase;
import com.om.http.application.adapter.dto.AuthorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorsControllerTest {

    @Autowired
    MockMvcTester mockMvc;

    @MockitoBean
    AuthorsUseCase authorsUseCase;

    @DisplayName("Given a list of authors, when retrieving authors, then the list is not null")
    @Test
    void testRetrieveAuthors() {

        Author author1 = new Author(UUID.randomUUID(), "johndoe", "test@test.com");
        Author author2 = new Author(UUID.randomUUID(), "johnsmith", "johnsmith@test.com");
        List<Author> authorList = List.of(author1, author2);

        when(authorsUseCase.getAuthors()).thenReturn(authorList);

        assertThat(mockMvc.get().uri("/authors")
                .accept(MediaType.APPLICATION_JSON))
                .hasStatusOk()
                .bodyJson()
                .convertTo(AuthorDTO[].class)
                .satisfies(authors -> {

                    assertThat(authors).isNotEmpty().hasSize(2);

                    assertThat(authors[0].id()).isNotNull().isInstanceOf(UUID.class);
                    assertThat(authors[0].username()).isNotEmpty().isEqualTo("johndoe");
                    assertThat(authors[0].email()).isNotEmpty().isEqualTo("test@test.com");

                    assertThat(authors[1].id()).isNotNull().isInstanceOf(UUID.class);
                    assertThat(authors[1].username()).isNotEmpty().isEqualTo("johnsmith");
                    assertThat(authors[1].email()).isNotEmpty().isEqualTo("johnsmith@test.com");
                });

        verify(authorsUseCase).getAuthors();

    }

}