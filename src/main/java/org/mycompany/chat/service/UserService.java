package org.mycompany.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.User;
import org.mycompany.chat.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = repository.getUserByEmail(lowercaseLogin);
        return userFromDatabase.orElseThrow(
            () -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
                "database"));
    }

    @PostConstruct
    private void post() {
        User a = User.builder()
            .email("lala@mail.com")
            .password("password")
            .authorities("ROLE_USER")
            .build();
        repository.save(a);
    }

}
