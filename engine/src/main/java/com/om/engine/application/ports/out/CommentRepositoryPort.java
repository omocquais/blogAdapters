package com.om.engine.application.ports.out;

import com.om.engine.application.domain.Comment;

import java.util.List;

public interface CommentRepositoryPort {

    List<Comment> findAll();

}
