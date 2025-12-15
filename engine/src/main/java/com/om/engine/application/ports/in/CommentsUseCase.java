package com.om.engine.application.ports.in;

import com.om.engine.application.domain.Comment;

import java.util.List;

public interface CommentsUseCase {

    /***
     * Get all comments
     * @return list of comments
     */
    List<Comment> getComments();
}
