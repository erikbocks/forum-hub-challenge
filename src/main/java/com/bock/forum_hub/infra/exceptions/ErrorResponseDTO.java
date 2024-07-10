package com.bock.forum_hub.infra.exceptions;

import org.springframework.validation.FieldError;

public record ErrorResponseDTO(
        String field,
        String message
) {
    public ErrorResponseDTO(FieldError e) {
        this(e.getField(), e.getDefaultMessage());
    }
}
