package com.om.engine.application.services;

import com.om.engine.application.domain.Comment;
import com.om.engine.application.ports.in.CommentsUseCase;
import com.om.engine.application.ports.out.CommentRepositoryPort;

import java.util.List;

public class CommentService implements CommentsUseCase {

    private final CommentRepositoryPort commentRepositoryPort;

    public CommentService(CommentRepositoryPort commentRepositoryPort) {
        this.commentRepositoryPort = commentRepositoryPort;
    }

    @Override
    public List<Comment> getComments() {
        return commentRepositoryPort.findAll();
    }
}
