package ru.berdnikov.springjwtproject.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.service.TokenService;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on TokenServiceImpl.
 * @project SpringJWTProject
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String ID_CLAIM = "id";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.tokenExpiration}")
    private Duration tokenExpiration;

    @Value("${jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    @Override
    public String generateToken(String username, Long id, List<String> roles) {
        return generateToken(username, id, roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
    }

    @Override
    public String generateToken(String username, Long id, Collection<? extends GrantedAuthority> authorities) {
        String authoritiesString = authorities.stream()
                .map(authority -> ROLE_PREFIX + authority.getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, authoritiesString)
                .claim(ID_CLAIM, id)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime() + tokenExpiration.toMillis())))
                .compact();
    }

    @Override
    public UsernamePasswordAuthenticationToken toAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String subject = claims.getSubject();
        Long id = claims.get(ID_CLAIM, Long.class);
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        AppUser principal = new AppUser(subject, id.toString(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, getSigningKey());
    }

    private boolean validateToken(String token, Key secret) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("Invalid signature: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
        }
        return false;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
