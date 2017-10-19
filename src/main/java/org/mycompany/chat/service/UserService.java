package org.mycompany.chat.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.User;
import org.mycompany.chat.domain.UserSecurityDetails;
import org.mycompany.chat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    public User createUser(String email, String password, String firstName, String lastName) {

        User newUser = User.builder()
            .firstName(firstName)
            .lastName(lastName)
            .securityDetails(
                UserSecurityDetails.builder()
                    .email(email)
                    .authorities("ROLE_USER")
                    .password(passwordEncoder.encode("password"))
                    .build()
            ).build();

        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    @PostConstruct
    private void post() {
        createUser("lala@mail.com", "password", "Gri", "Mo");
    }
}
