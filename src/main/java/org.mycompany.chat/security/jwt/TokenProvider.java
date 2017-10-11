package org.mycompany.chat.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.Author;
import org.mycompany.chat.domain.Authority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID = "userId";

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    public TokenProvider() {
    }

    @PostConstruct
    public void init() {
        this.secretKey = "uBaf7ZQtnU8Ny6L2bzul";

        this.tokenValidityInMilliseconds =
            1_800_000;
        this.tokenValidityInMillisecondsForRememberMe =
            1_800_000;
    }

    public String createToken(Author author) {
        String authorities = author.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        Claims claims = Jwts.claims().setSubject(author.getUsername());
        claims.put(USER_ID, author.getId() + "");
        claims.put(AUTHORITIES_KEY, authorities);
        return Jwts.builder()
            .setSubject(author.getUsername())
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setExpiration(validity)
            .compact();
    }

    Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        Set<Authority> authorities = Arrays.stream(((String) claims.get(AUTHORITIES_KEY)).split(","))
            .map(Authority::new)
            .collect(Collectors.toSet());

        Author user = Author.builder()
            .username(claims.getSubject())
            .authorities(authorities)
            .id(Long.parseLong((String) claims.get(USER_ID)))
            .build();

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
