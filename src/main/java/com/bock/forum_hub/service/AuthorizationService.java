package com.bock.forum_hub.service;

import com.bock.forum_hub.domain.user.User;
import com.bock.forum_hub.domain.user.dtos.UserRegisterDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizationService extends UserDetailsService {
    User registerUser(UserRegisterDTO data);
}
