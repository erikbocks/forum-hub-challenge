package com.bock.forum_hub.service;

import com.bock.forum_hub.domain.user.User;

public interface TokenService {
    String generateToken(User user);
    Long recoverToken(String token);
}
