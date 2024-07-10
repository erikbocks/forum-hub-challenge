package com.bock.forum_hub.repositories;

import com.bock.forum_hub.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByNameEqualsIgnoreCase(String name);
}
