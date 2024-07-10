package com.bock.forum_hub.domain.topic.dtos;

import com.bock.forum_hub.domain.course.dtos.CourseRegisterDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record TopicRegisterData(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @Valid
        CourseRegisterDTO course
) {
}
