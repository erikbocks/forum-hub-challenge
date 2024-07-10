package com.bock.forum_hub.service.impls;

import com.bock.forum_hub.domain.user.User;
import com.bock.forum_hub.domain.user.dtos.UserRegisterDTO;
import com.bock.forum_hub.repositories.UserRepository;
import com.bock.forum_hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(UserRegisterDTO data) {
        return null;
    }
}
