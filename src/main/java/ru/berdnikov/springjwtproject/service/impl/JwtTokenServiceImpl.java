package ru.berdnikov.springjwtproject.service.impl;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.config.JWTConfig;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.JwtTokenService;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on JwtTokenServiceImpl.
 * @project SpringJWTProject
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String ID_CLAIM = "id";
    private final JWTConfig jwtConfig;

    @Override
    public String generatePasswordTokenForUser(UserModel userModel) {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtConfig.getTokenExpiration().toMillis());

        String authoritiesString = userModel.getRoles().stream()
                .map(role -> ROLE_PREFIX + new SimpleGrantedAuthority(role.name()).getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userModel.getUsername())
                .claim(AUTHORITIES_KEY, authoritiesString)
                .claim(ID_CLAIM, userModel.getId())
                .signWith(jwtConfig.getSigningKey(), SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .compact();
    }

    @Override
    public UsernamePasswordAuthenticationToken toAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String subject = claims.getSubject();
        Long id = claims.get(ID_CLAIM, Long.class);
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        AppUser principal = new AppUser(subject, id, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            if (!SignatureAlgorithm.HS512.getValue().equals(claimsJws.getHeader().getAlgorithm())) {
                log.error("Unsupported JWT algorithm: {}", claimsJws.getHeader().getAlgorithm());
                return false;
            }
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
}
