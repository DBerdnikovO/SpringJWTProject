package ru.berdnikov.springjwtproject.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.service.TokenService;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on TokenServiceImpl.
 * @project SpringJWTProject
 */
@Slf4j
@Component
public class TokenServiceImpl implements TokenService {
    private static final String AUTHORITIES_KEY = "auth";

    private static final String ID_CLAIM = "id";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.tokenExpiration}")
    private Duration tokenExpiration;

    public String generateToken(Authentication authentication, String id) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expirationDateTime = now.plus(tokenExpiration);

        Date issueDate = Date.from(now.toInstant());
        Date expirationDate = Date.from(expirationDateTime.toInstant());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(ID_CLAIM, id)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .compact();
    }

    public Authentication toAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String id = claims.get(ID_CLAIM, String.class);

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        String subject = claims.getSubject();


        AppUser principal = new AppUser(subject, id, "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.info("Invalid JWT signature: " + e.getMessage());
            log.debug("Exception " + e.getMessage(), e);
            return false;
        }
    }
}
