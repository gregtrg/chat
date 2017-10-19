package org.mycompany.chat.service;

import lombok.RequiredArgsConstructor;
import org.mycompany.chat.domain.JwtToken;
import org.mycompany.chat.domain.UserSecurityDetails;
import org.mycompany.chat.repository.TokenRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository repository;

    public Long getUserIdByTokenIfExists(String jwt) {
        JwtToken token = repository.findOneByToken(jwt);
        return token != null ? token.getUser() != null ? token.getUser().getId() : null : null;
    }

    public void createToken(String token, UserDetails userDetails) {
        repository.save(new JwtToken(token, (UserSecurityDetails) userDetails));
    }
}
