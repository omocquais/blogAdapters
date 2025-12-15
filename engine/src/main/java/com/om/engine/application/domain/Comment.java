package com.om.engine.application.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record Comment(
        UUID id,
        String authorName,
        String authorEmail,
        String content,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime commentDate,

        UUID articleId
) {
}
