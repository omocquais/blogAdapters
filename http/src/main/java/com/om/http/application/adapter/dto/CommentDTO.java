package com.om.http.application.adapter.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDTO (
        UUID id,
        String authorName,
        String authorEmail,
        String content,
        LocalDateTime commentDate,
        UUID articleId
){
}
