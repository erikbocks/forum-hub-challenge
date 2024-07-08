package com.bock.forum_hub.controllers;

import com.bock.forum_hub.domain.user.User;
import com.bock.forum_hub.domain.user.dtos.UserInfoDTO;
import com.bock.forum_hub.domain.user.dtos.UserRegisterDTO;
import com.bock.forum_hub.service.AuthorizationService;
import com.bock.forum_hub.util.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthorizationService authService;
    @Autowired
    private RestResponse restResponse;

    @PostMapping("/register")
    public ResponseEntity<RestResponse> saveNewUser(@RequestBody @Valid UserRegisterDTO data, UriComponentsBuilder builder) {
        User newUser = authService.registerUser(data);

        URI uri = builder.path("users/{id}").buildAndExpand(newUser.getId()).toUri();

        return restResponse.created(uri,"Usuário cadastrado com sucesso!", new UserInfoDTO(newUser));
    }
}
