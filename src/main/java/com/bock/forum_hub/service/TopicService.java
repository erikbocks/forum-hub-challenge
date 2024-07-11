package com.bock.forum_hub.service;

import com.bock.forum_hub.domain.topic.Topic;
import com.bock.forum_hub.domain.topic.dtos.TopicDetailDTO;
import com.bock.forum_hub.domain.topic.dtos.TopicRegisterData;
import com.bock.forum_hub.domain.topic.dtos.TopicUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicService {
    List<TopicDetailDTO> findAll(Pageable pageable, String username, String course, Integer year);

    Topic saveTopic(TopicRegisterData data, String username);

    Topic detailTopic(Long topicId, String username);

    Topic updateTopic(Long topicId, TopicUpdateDTO data, String username);

    void deleteTopic(Long id, String username);
}
