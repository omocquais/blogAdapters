package com.om.http.application.adapter;

import com.om.engine.application.ports.in.CommentsUseCase;
import com.om.http.application.adapter.dto.CommentDTO;
import com.om.http.application.adapter.mapper.CommentMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {

    private final CommentsUseCase commentsUseCase;
    private final CommentMapper commentMapper;

    public CommentController(CommentsUseCase commentsUseCase, CommentMapper commentMapper) {
        this.commentsUseCase = commentsUseCase;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/comments")
    public List<CommentDTO> getComments() {
        return commentsUseCase.getComments().stream()
                .map(commentMapper::toDTO)
                .toList();
    }
}
