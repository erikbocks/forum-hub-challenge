package com.bock.forum_hub.service.impls;

import com.bock.forum_hub.domain.user.User;
import com.bock.forum_hub.domain.user.dtos.UserRegisterDTO;
import com.bock.forum_hub.infra.exceptions.customs.ValidationException;
import com.bock.forum_hub.repositories.UserRepository;
import com.bock.forum_hub.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.bock.forum_hub.infra.security.SecurityConfigurations.getPasswordEnconder;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(UserRegisterDTO data) {
        Boolean dbEmailUser = userRepository.existsUserByEmail(data.email().trim());

        if (dbEmailUser) {
            throw new ValidationException("Email já cadastrado no banco de dados.");
        }

        Boolean dbNameUser = userRepository.existsUserByName(data.name().trim());

        if (dbNameUser) {
            throw new ValidationException("Nome já cadastrado no banco de dados.");
        }

        PasswordEncoder passwordEncoder = getPasswordEnconder();
        User newUser = new User(data, passwordEncoder.encode(data.password().trim()));
        userRepository.saveAndFlush(newUser);

        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findBySubject(username.trim());
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao recuperar usuario pelo nome", ex);
        }
    }
}
