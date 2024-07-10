package com.bock.forum_hub.service;

import com.bock.forum_hub.domain.user.User;
import com.bock.forum_hub.domain.user.dtos.UserRegisterDTO;

public interface UserService {
    User saveUser(UserRegisterDTO data);
}
