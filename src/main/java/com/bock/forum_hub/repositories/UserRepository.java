package com.bock.forum_hub.repositories;

import com.bock.forum_hub.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findUserById(Long userId);

    @Query("Select u from User u where u.name = :username")
    UserDetails findBySubject(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByName(String name);
}
