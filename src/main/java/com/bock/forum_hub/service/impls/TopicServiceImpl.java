package com.bock.forum_hub.service.impls;

import com.bock.forum_hub.domain.course.Course;
import com.bock.forum_hub.domain.topic.Topic;
import com.bock.forum_hub.domain.topic.dtos.TopicDetailDTO;
import com.bock.forum_hub.domain.topic.dtos.TopicRegisterData;
import com.bock.forum_hub.domain.topic.dtos.TopicUpdateDTO;
import com.bock.forum_hub.domain.user.User;
import com.bock.forum_hub.infra.exceptions.customs.ValidationException;
import com.bock.forum_hub.repositories.CourseRepository;
import com.bock.forum_hub.repositories.TopicRepository;
import com.bock.forum_hub.repositories.UserRepository;
import com.bock.forum_hub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<TopicDetailDTO> findAll(Pageable pageable, String username, String course, Integer year) {
        Optional<User> dbUser = userRepository.findUserByName(username);

        User user = dbUser.orElseThrow(() -> new IllegalArgumentException("Nenhum usuario encontrado no token."));

        Page<Topic> topics;

        if (course != null && year != null) {
            topics = topicRepository.findAllByCourseAndYear(pageable, user.getId(), course, year);
            return transformToDTO(topics);
        }

        if (course == null && year != null) {
            topics = topicRepository.findAllByYear(pageable, user.getId(), year);
            return transformToDTO(topics);
        }

        if (course != null) {
            topics = topicRepository.findAllByCourse(pageable, user.getId(), course);
            return transformToDTO(topics);
        }

        topics = topicRepository.findAllByAuthorId(pageable, user.getId());
        return transformToDTO(topics);
    }

    private List<TopicDetailDTO> transformToDTO(Page<Topic> topics) {
        return topics.stream()
                .map(TopicDetailDTO::new)
                .toList();
    }

    @Override
    public Topic saveTopic(TopicRegisterData data, String username) {
        User user = recoverUser(username);

        validateUniqueMessage(data.message());
        validateUniqueTitle(data.title());

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

    @Override
    public Topic detailTopic(Long topicId, String username) {
        User user = recoverUser(username);

        Optional<Topic> dbTopic = topicRepository.findByIdAndAuthorId(topicId, user.getId());

        return dbTopic.orElseThrow(() -> new IllegalArgumentException("Nenhum tópico encontrado com esse id."));
    }

    @Override
    public Topic updateTopic(Long topicId, TopicUpdateDTO data, String username) {
        User user = recoverUser(username);

        Optional<Topic> dbTopic = topicRepository.findByIdAndAuthorId(topicId, user.getId());

        if (dbTopic.isEmpty()) {
            throw new IllegalArgumentException("Nenhum tópico encontrado para esse id.");
        }

        Topic topic = dbTopic.get();

        if (!topic.getTitle().equals(data.title())) {
            validateUniqueTitle(data.title());
        }

        if (!topic.getMessage().equals(data.message())) {
            validateUniqueMessage(data.message());
        }

        topic.update(data);

        return topicRepository.saveAndFlush(topic);
    }

    @Override
    public void deleteTopic(Long id, String username) {
        User user = recoverUser(username);

        Boolean dbTopic = topicRepository.existsByIdAndAuthorId(id, user.getId());

        if (!dbTopic) {
            throw new IllegalArgumentException("Nenhum tópico encontrado com esse id.");
        }

        topicRepository.deleteById(id);
    }

    private void validateUniqueMessage(String message) {
        Boolean notUniqueMessage = topicRepository.existsByMessageEqualsIgnoreCase(message.trim());

        if (notUniqueMessage) {
            throw new ValidationException("Mensagem já cadastrado no banco de dados.");
        }
    }

    private void validateUniqueTitle(String title) {
        Boolean titleNotUnique = topicRepository.existsByTitleEqualsIgnoreCase(title.trim());

        if (titleNotUnique) {
            throw new ValidationException("Titulo já cadastrado no banco de dados.");
        }
    }

    private User recoverUser(String username) {
        Optional<User> dbUser = userRepository.findUserByName(username);

        return dbUser.orElseThrow(() -> new IllegalArgumentException("Nenhum usuário encontrado no token."));
    }
}
