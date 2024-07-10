package com.bock.forum_hub.domain.topic;

import com.bock.forum_hub.domain.answer.Answer;
import com.bock.forum_hub.domain.course.Course;
import com.bock.forum_hub.domain.topic.dtos.TopicRegisterData;
import com.bock.forum_hub.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topics")
@Entity(name = "Topic")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;
    private String title;
    private String message;
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers;

    public Topic(TopicRegisterData data, Course course, User user) {
        this.title = data.title();
        this.message = data.message();
        this.course = course;
        this.status = Status.NAO_RESPONDIDO;
        this.author = user;
    }

    @PrePersist
    public void automaticDate() {
        this.creationDate = LocalDateTime.now();
    }
}
