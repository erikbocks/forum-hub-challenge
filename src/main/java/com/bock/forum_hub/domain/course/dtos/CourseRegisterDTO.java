package com.bock.forum_hub.domain.course.dtos;

import jakarta.validation.constraints.NotBlank;

public record CourseRegisterDTO(
        @NotBlank
        String name,
        @NotBlank
        String category
) {
}
