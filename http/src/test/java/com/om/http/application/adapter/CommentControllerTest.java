package com.om.http.application.adapter;

import com.om.engine.application.domain.Comment;
import com.om.engine.application.ports.in.CommentsUseCase;
import com.om.http.application.adapter.dto.CommentDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    MockMvcTester mockMvc;

    @MockitoBean
    CommentsUseCase commentsUseCase;

    @DisplayName("Given a list of comments, when retrieving comments, then the list is not null")
    @Test
    void testRetrieveComments() {

        LocalDateTime commentDate1 = LocalDateTime.of(2024, 1,
                1, 10, 0);
        UUID articleId1 = UUID.randomUUID();

        Comment comment1 = new Comment(UUID.randomUUID(), "johndoe", "test@test.com", "This is a comment",
                commentDate1, articleId1);

        LocalDateTime commentDate2 = LocalDateTime.of(2025, 4,
                5, 15, 34);
        UUID articleId2 = UUID.randomUUID();
        Comment comment2 = new Comment(UUID.randomUUID(), "johnsmith", "johnsmith@test.com", "This is another comment",
                commentDate2, articleId2);

        List<Comment> commentList = List.of(comment1, comment2);

        when(commentsUseCase.getComments()).thenReturn(commentList);

        assertThat(mockMvc.get().uri("/comments")
                .accept(MediaType.APPLICATION_JSON))
                .hasStatusOk()
                .bodyJson()
                .convertTo(CommentDTO[].class)
                .satisfies(comments -> {

                    assertThat(comments).isNotEmpty().hasSize(2);

                    assertThat(comments[0].id()).isNotNull().isInstanceOf(UUID.class);
                    assertThat(comments[0].authorName()).isNotEmpty().isEqualTo("johndoe");
                    assertThat(comments[0].authorEmail()).isNotEmpty().isEqualTo("test@test.com");
                    assertThat(comments[0].content()).isNotEmpty().isEqualTo("This is a comment");
                    assertThat(comments[0].commentDate()).isNotNull().isEqualTo(commentDate1);
                    assertThat(comments[0].articleId()).isNotNull().isEqualTo(articleId1);

                    assertThat(comments[1].id()).isNotNull().isInstanceOf(UUID.class);
                    assertThat(comments[1].authorName()).isNotEmpty().isEqualTo("johnsmith");
                    assertThat(comments[1].authorEmail()).isNotEmpty().isEqualTo("johnsmith@test.com");
                    assertThat(comments[1].content()).isNotEmpty().isEqualTo("This is another comment");
                    assertThat(comments[1].commentDate()).isNotNull().isEqualTo(commentDate2);
                    assertThat(comments[1].articleId()).isNotNull().isEqualTo(articleId2);

                });

        verify(commentsUseCase).getComments();

    }


}