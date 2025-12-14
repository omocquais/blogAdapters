package com.om.http.application.adapter.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ArticleDTO(UUID id,
                         String title,
                         String content,
                         LocalDateTime publishDate,
                         boolean isPublished,
                         UUID authorId) {
}
