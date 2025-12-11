package com.om.http.application.adapter.dto;

import java.util.UUID;

public record AuthorDTO(UUID id, String username, String email) {
}
