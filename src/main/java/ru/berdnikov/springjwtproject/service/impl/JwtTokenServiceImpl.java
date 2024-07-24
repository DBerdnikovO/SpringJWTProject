package ru.berdnikov.springjwtproject.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.config.JWTConfig;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.JwtTokenService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on JwtTokenServiceImpl.
 * @project SpringJWTProject
 */
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
}
