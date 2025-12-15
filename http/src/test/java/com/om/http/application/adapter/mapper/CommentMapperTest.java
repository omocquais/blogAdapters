package com.om.http.application.adapter.mapper;

import com.om.engine.application.domain.Comment;
import com.om.http.application.adapter.dto.CommentDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CommentMapperTest {

    @DisplayName("Given an Comment, when toDTO is called, then an CommentDTO is returned")
    @Test
    void toDTO() {

        LocalDateTime commentDate1 = LocalDateTime.of(2024, 1,
                1, 10, 0);
        UUID articleId1 = UUID.randomUUID();

        UUID uuid = UUID.randomUUID();
        Comment comment1 = new Comment(uuid, "johndoe", "test@test.com", "This is a comment",
                commentDate1               , articleId1);

        CommentDTO commentDTO = new CommentMapper().toDTO(comment1);

        assertThat(commentDTO).isNotNull();
        assertThat(commentDTO.id()).isEqualTo(uuid);
        assertThat(commentDTO.authorName()).isEqualTo("johndoe");
        assertThat(commentDTO.authorEmail()).isEqualTo("test@test.com");
        assertThat(commentDTO.content()).isEqualTo("This is a comment");
        assertThat(commentDTO.commentDate()).isEqualTo(commentDate1);
        assertThat(commentDTO.articleId()).isEqualTo(articleId1);

    }

}