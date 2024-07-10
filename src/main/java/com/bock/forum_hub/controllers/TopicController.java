package com.bock.forum_hub.controllers;

import com.bock.forum_hub.domain.topic.Topic;
import com.bock.forum_hub.domain.topic.dtos.TopicDetailDTO;
import com.bock.forum_hub.domain.topic.dtos.TopicFullDetailDTO;
import com.bock.forum_hub.domain.topic.dtos.TopicRegisterData;
import com.bock.forum_hub.service.TopicService;
import com.bock.forum_hub.util.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private RestResponse response;

    @GetMapping
    public ResponseEntity<RestResponse> getTopics(
            HttpServletRequest request,
            @PageableDefault(sort = {"creationDate"}) Pageable pageable,
            @RequestParam(required = false, name = "curso") String course,
            @RequestParam(required = false, name = "ano") Integer year) {
        String user = request.getUserPrincipal().getName();

        List<TopicDetailDTO> topics = topicService.findAll(pageable, user, course, year);

        return response.ok("teste", topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse> detailTopic(HttpServletRequest request, @PathVariable @NotNull @Positive Long id) {
        String user = request.getUserPrincipal().getName();

        Topic topic = topicService.detailTopic(id, user);

        return response.ok("Informaçoes sobre o topico adquiridas com sucesso,", new TopicFullDetailDTO(topic));
    }

    @PostMapping
    public ResponseEntity<RestResponse> registerTopic(@RequestBody @Valid TopicRegisterData data, HttpServletRequest request, UriComponentsBuilder builder) {
        String user = request.getUserPrincipal().getName();

        Topic newTopic = topicService.saveTopic(data, user);
        URI uri = builder.path("/topicos/{id}").buildAndExpand(newTopic.getId()).toUri();

        return response.created(uri, "Tópico criado com sucesso", new TopicDetailDTO(newTopic));
    }
}
