package com.bock.forum_hub.domain.topic.dtos;

import com.bock.forum_hub.domain.answer.dtos.AnswerDetailDTO;
import com.bock.forum_hub.domain.topic.Status;
import com.bock.forum_hub.domain.topic.Topic;

import java.time.LocalDateTime;
import java.util.List;

public record TopicFullDetailDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String nomeAutor,
        Status status,
        List<AnswerDetailDTO> respostas
) {
    public TopicFullDetailDTO(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreationDate(), topic.getAuthor().getName(), topic.getStatus(), topic.getAnswers().stream().map(AnswerDetailDTO::new).toList());
    }
}
