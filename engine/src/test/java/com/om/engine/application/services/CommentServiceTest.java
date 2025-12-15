package com.om.engine.application.services;

import com.om.engine.application.domain.Comment;
import com.om.engine.application.ports.out.CommentRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepositoryPort commentRepositoryPort;

    @DisplayName("Given a list of comments, when retrieving comments, then the list is not null")
    @Test
    void getComments() {
        List<Comment> comments = new CommentService(commentRepositoryPort).getComments();
        assertNotNull(comments);
        verify(commentRepositoryPort).findAll();
    }

}