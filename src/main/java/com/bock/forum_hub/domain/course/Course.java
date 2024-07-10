package com.bock.forum_hub.domain.course;

import com.bock.forum_hub.domain.course.dtos.CourseRegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "courses")
@Entity(name = "Course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;

    public Course(CourseRegisterDTO course) {
        this.name = course.name();
        this.category = Category.fromString(course.category());
    }
}
