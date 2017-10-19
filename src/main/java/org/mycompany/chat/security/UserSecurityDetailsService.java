package org.mycompany.chat.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.UserSecurityDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserSecurityDetailsService implements UserDetailsService {

    private final UserSecurityDetailsRepository repository;

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<UserSecurityDetails> userFromDatabase = repository.findOneByEmail(lowercaseLogin);
        return userFromDatabase.orElseThrow(
            () -> new UsernameNotFoundException("UserSecurityDetails " + lowercaseLogin + " was not found in the " +
                "database"));
    }

}
