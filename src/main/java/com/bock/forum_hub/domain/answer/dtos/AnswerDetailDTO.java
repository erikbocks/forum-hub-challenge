package com.bock.forum_hub.domain.answer.dtos;

import com.bock.forum_hub.domain.answer.Answer;

import java.time.LocalDateTime;

public record AnswerDetailDTO(
        String mensagem,
        LocalDateTime dataCriacao,
        String nomeAutor
) {
    public AnswerDetailDTO(Answer answer) {
        this(answer.getMessage(), answer.getCreationDate(), answer.getAuthor().getName());
    }
}
