package com.om.http.application.adapter.mapper;

import com.om.engine.application.domain.Author;
import com.om.http.application.adapter.dto.AuthorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthorMapperTest {

    @DisplayName("Given an Author, when toDTO is called, then an AuthorDTO is returned")
    @Test
    void toDTO() {
        UUID uuid = UUID.randomUUID();

        AuthorDTO authorDTO = new AuthorMapper().toDTO(new Author(uuid, "username", "test@test.com"));

        assertThat(authorDTO).isNotNull();
        assertThat(authorDTO.username()).isEqualTo("username");
        assertThat(authorDTO.email()).isEqualTo("test@test.com");
        assertThat(authorDTO.id()).isEqualTo(uuid);
    }

    @DisplayName("Given an invalid parameter, when toDTO is called, iy should throw an IllegalArgumentException")
    @Test
    void invalidAuthor() {
        AuthorMapper authorMapper = new AuthorMapper();
        assertThatThrownBy(() -> authorMapper.toDTO(null)).isInstanceOf(IllegalArgumentException.class);
    }
}