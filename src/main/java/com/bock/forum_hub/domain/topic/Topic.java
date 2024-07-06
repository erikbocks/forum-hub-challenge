package com.bock.forum_hub.domain.topic;

import com.bock.forum_hub.domain.course.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String status;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
