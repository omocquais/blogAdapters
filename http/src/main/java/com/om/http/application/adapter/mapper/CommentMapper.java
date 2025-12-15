package com.om.http.application.adapter.mapper;

import com.om.engine.application.domain.Comment;
import com.om.http.application.adapter.dto.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDTO toDTO(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }
        return new CommentDTO(comment.id(), comment.authorName(), comment.authorEmail(), comment.content(), comment.commentDate(), comment.articleId());
    }
}
