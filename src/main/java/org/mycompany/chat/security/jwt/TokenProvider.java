package org.mycompany.chat.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mycompany.chat.domain.UserSecurityDetails;
import org.mycompany.chat.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.parser;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID = "userId";
    private final TokenService tokenService;

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    @PostConstruct
    public void init() {
        this.secretKey = "uBaf7ZQtnU8Ny6L2bzul";

        this.tokenValidityInMilliseconds =
            1_800_000;
    }

    public String createToken(Authentication authentication) {
        UserSecurityDetails user = (UserSecurityDetails) authentication.getPrincipal();
        String authorities = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(USER_ID, user.getId() + "");
        claims.put(AUTHORITIES_KEY, authorities);
        String token = Jwts.builder()
            .setSubject(user.getUsername())
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setExpiration(validity)
            .compact();
        tokenService.createToken(token, user);
        return token;
    }

    Authentication getAuthentication(String token) {
        Claims claims = parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        UserSecurityDetails userSecurityDetails = UserSecurityDetails.builder()
            .email(claims.getSubject())
            .authorities((String) claims.get(AUTHORITIES_KEY))
            .id(Long.parseLong((String) claims.get(USER_ID)))
            .build();

        return new UsernamePasswordAuthenticationToken(userSecurityDetails, token, userSecurityDetails.getAuthorities());
    }

    boolean validateToken(String authToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();
            Long id = Long.parseLong((String) claims.get(USER_ID));
            return Objects.equals(id, tokenService.getUserIdByTokenIfExists(authToken));

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
