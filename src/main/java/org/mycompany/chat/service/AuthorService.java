package org.mycompany.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.Authority;
import org.mycompany.chat.domain.Author;
import org.mycompany.chat.repository.AuthorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService implements UserDetailsService {

    private final AuthorRepository repository;

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<Author> userFromDatabase = repository.getAuthorByUsername(lowercaseLogin);
        return userFromDatabase.orElseThrow(
            () -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
                "database"));
    }

    @PostConstruct
    private void post() {
        Set<Authority> as = newHashSet(Authority.builder()
            .authority("ROLE_USER").build());
        Author a = Author.builder()
            .username("user")
            .password("password")
            .authorities(as)
            .build();
        repository.save(a);
    }
}
