package com.bock.forum_hub.service.impls;

import com.bock.forum_hub.domain.course.Course;
import com.bock.forum_hub.domain.topic.Topic;
import com.bock.forum_hub.domain.topic.dtos.TopicRegisterData;
import com.bock.forum_hub.domain.user.User;
import com.bock.forum_hub.infra.exceptions.customs.ValidationException;
import com.bock.forum_hub.repositories.CourseRepository;
import com.bock.forum_hub.repositories.TopicRepository;
import com.bock.forum_hub.repositories.UserRepository;
import com.bock.forum_hub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Topic saveTopic(TopicRegisterData data, String name) {
        Optional<User> dbUser = userRepository.findUserByName(name);

        User user = dbUser.orElseThrow(() -> new RuntimeException("Nenhum usuario encontrado no token."));

        Boolean uniqueTitle = topicRepository.existsByTitleEqualsIgnoreCase(data.title().trim());

        if(uniqueTitle) {
            throw new ValidationException("Titulo já cadastrado no banco de dados.");
        }

        Boolean uniqueMessage = topicRepository.existsByMessageEqualsIgnoreCase(data.message().trim());

        if(uniqueMessage) {
            throw new ValidationException("Mensagem já cadastrado no banco de dados.");
        }

        Course course;

        Optional<Course> dbCourse = courseRepository.findByNameEqualsIgnoreCase(data.course().name());

        if (dbCourse.isEmpty()) {
            course = new Course(data.course());

            courseRepository.saveAndFlush(course);
        } else {
            course = dbCourse.get();
        }

        Topic topic = new Topic(data, course, user);

        topicRepository.saveAndFlush(topic);

        return topic;
    }
}
