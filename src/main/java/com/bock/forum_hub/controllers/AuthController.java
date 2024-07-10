package com.bock.forum_hub.controllers;

import com.bock.forum_hub.domain.user.User;
import com.bock.forum_hub.domain.user.dtos.UserInfoDTO;
import com.bock.forum_hub.domain.user.dtos.UserLoginDTO;
import com.bock.forum_hub.domain.user.dtos.UserLoginTokenDTO;
import com.bock.forum_hub.domain.user.dtos.UserRegisterDTO;
import com.bock.forum_hub.service.AuthorizationService;
import com.bock.forum_hub.service.TokenService;
import com.bock.forum_hub.util.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/registrar")
    public ResponseEntity<RestResponse> saveNewUser(@RequestBody @Valid UserRegisterDTO data, UriComponentsBuilder builder) {
        User newUser = authService.registerUser(data);

        URI uri = builder.path("users/{id}").buildAndExpand(newUser.getId()).toUri();

        return restResponse.created(uri, "Usuário cadastrado com sucesso!", new UserInfoDTO(newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse> authenticateUser(@RequestBody @Valid UserLoginDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.login().trim(), data.password().trim());
        var auth = authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();

        String token = tokenService.generateToken(user);

        return restResponse.ok("Usuário autenticado com sucesso.", new UserLoginTokenDTO(token, "Bearer"));
    }
}
