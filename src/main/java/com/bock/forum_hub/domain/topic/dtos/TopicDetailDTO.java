package com.bock.forum_hub.domain.topic.dtos;

import com.bock.forum_hub.domain.topic.Topic;

import java.time.LocalDateTime;

public record TopicDetailDTO(Long id, String title, String message, LocalDateTime creationDate) {
    public TopicDetailDTO(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreationDate());
    }
}
