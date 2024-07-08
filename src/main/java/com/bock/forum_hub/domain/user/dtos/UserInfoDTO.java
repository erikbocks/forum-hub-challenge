package com.bock.forum_hub.domain.user.dtos;

import com.bock.forum_hub.domain.user.User;

public record UserInfoDTO(
        Long id,
        String name,
        String email
) {
    public UserInfoDTO(User newUser) {
        this(newUser.getId(), newUser.getName(), newUser.getEmail());
    }
}
