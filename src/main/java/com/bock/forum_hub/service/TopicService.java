package com.bock.forum_hub.service;

import com.bock.forum_hub.domain.topic.Topic;
import com.bock.forum_hub.domain.topic.dtos.TopicRegisterData;

public interface TopicService {
    Topic saveTopic(TopicRegisterData data, String name);
}
