package ru.berdnikov.springjwtproject.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.model.RefreshToken;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.RefreshTokenService;
import ru.berdnikov.springjwtproject.service.SecurityService;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on SecurityServiceImpl.
 * @project SpringJWTProject
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final RefreshTokenService tokenService;

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
    public String generatePasswordTokenForUser(UserModel userModel) {
        Date expirationDate = new Date(System.currentTimeMillis() + tokenExpiration.toMillis());

        String authoritiesString = userModel.getRoles().stream()
                .map(role -> ROLE_PREFIX + new SimpleGrantedAuthority(role.name()).getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userModel.getUsername())
                .claim(AUTHORITIES_KEY, authoritiesString)
                .claim(ID_CLAIM, userModel.getId())
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .compact();
    }

    @Override
    public String generateRefreshTokenForUser(UserModel userModel) {
        Date expirationDate = new Date(System.currentTimeMillis() + refreshTokenExpiration.toMillis());

        String token = Jwts.builder()
                .setSubject(userModel.getUsername())
                .setExpiration(expirationDate)
                .signWith(getSigningKey())
                .compact();

        tokenService.save(userModel.getId());

        return token;
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

    @Override
    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, getSigningKey());
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

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
