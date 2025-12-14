package com.om.engine.application.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record Article(

        UUID id,
        String title,
        String content,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime publishDate,
        boolean isPublished,
        UUID authorId

) {
}
