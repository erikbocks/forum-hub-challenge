package com.bock.forum_hub.domain.user.dtos;

public record UserLoginTokenDTO(
        String token,
        String type
) {
}
