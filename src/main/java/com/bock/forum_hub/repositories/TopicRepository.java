package com.bock.forum_hub.repositories;

import com.bock.forum_hub.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Boolean existsByTitleEqualsIgnoreCase(String title);
    Boolean existsByMessageEqualsIgnoreCase(String message);
}
