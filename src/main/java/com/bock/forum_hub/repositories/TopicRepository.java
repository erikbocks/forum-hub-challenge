package com.bock.forum_hub.repositories;

import com.bock.forum_hub.domain.course.Course;
import com.bock.forum_hub.domain.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Boolean existsByTitleEqualsIgnoreCase(String title);

    Boolean existsByMessageEqualsIgnoreCase(String message);

    @Query("select t from Topic t" +
            " where t.author.id = :owner" +
            " and t.course.name = :course" +
            " and year(t.creationDate) = :year")
    Page<Topic> findAllByCourseAndYear(Pageable pageable, Long owner, String course, Integer year);

    @Query("select t from Topic t" +
            " where t.author.id = :owner" +
            " and t.course.name = :course")
    Page<Topic> findAllByCourse(Pageable pageable, Long owner, String course);

    @Query("select t from Topic t" +
            " where t.author.id = :owner" +
            " and year(t.creationDate) = :year")
    Page<Topic> findAllByYear(Pageable pageable, Long owner, Integer year);

    Page<Topic> findAllByAuthorId(Pageable pageable, Long owner);

    Optional<Topic> findByIdAndAuthorId(Long topicId, Long owner);
}
