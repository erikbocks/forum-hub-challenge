package com.bock.forum_hub.domain.answer;

import com.bock.forum_hub.domain.topic.Topic;
import com.bock.forum_hub.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "answers")
@Entity(name = "Answer")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    Long id;
    String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic")
    Topic topic;
    LocalDateTime creationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    User author;
}
