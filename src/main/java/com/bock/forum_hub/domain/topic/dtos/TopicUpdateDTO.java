package com.bock.forum_hub.domain.topic.dtos;

import jakarta.validation.constraints.NotBlank;

public record TopicUpdateDTO(
        @NotBlank
        String title,
        @NotBlank
        String message
) {
}
