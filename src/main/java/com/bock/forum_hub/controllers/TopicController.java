package com.bock.forum_hub.controllers;

import com.bock.forum_hub.domain.topic.Topic;
import com.bock.forum_hub.domain.topic.dtos.TopicRegisterData;
import com.bock.forum_hub.domain.topic.dtos.TopicDetailDTO;
import com.bock.forum_hub.service.TopicService;
import com.bock.forum_hub.util.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private RestResponse response;

    @PostMapping
    public ResponseEntity<RestResponse> registerTopic(@RequestBody @Valid TopicRegisterData data, HttpServletRequest request, UriComponentsBuilder builder) {
        String user = request.getUserPrincipal().getName();

        Topic newTopic = topicService.saveTopic(data, user);
        URI uri = builder.path("/topicos/{id}").buildAndExpand(newTopic.getId()).toUri();

        return response.created(uri, "Tópico criado com sucesso", new TopicDetailDTO(newTopic));
    }
}
