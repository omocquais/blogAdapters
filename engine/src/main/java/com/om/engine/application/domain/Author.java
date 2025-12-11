package com.om.engine.application.domain;

import java.util.UUID;

public record Author(UUID id, String username, String email) {
}
