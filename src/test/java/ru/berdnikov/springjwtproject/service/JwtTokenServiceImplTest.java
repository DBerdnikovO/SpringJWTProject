package ru.berdnikov.springjwtproject.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.berdnikov.springjwtproject.config.JWTConfig;
import ru.berdnikov.springjwtproject.model.RoleType;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.impl.JwtTokenServiceImpl;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author danilaberdnikov on JwtTokenServiceImplTest.
 * @project SpringJWTProject
 */
@ExtendWith(MockitoExtension.class)
class JwtTokenServiceImplTest {

    @Mock
    private JWTConfig jwtConfig;

    @InjectMocks
    private JwtTokenServiceImpl jwtTokenService;

    private UserModel userModel;
    private Key signingKey;

    @BeforeEach
    public void setUp() {
        signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        when(jwtConfig.getSigningKey()).thenReturn((SecretKey) signingKey);
        when(jwtConfig.getTokenExpiration()).thenReturn(Duration.ofHours(1));

        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setUsername("testuser");
        userModel.setPassword("password");
        userModel.setRoles(Set.of(RoleType.USER));
    }

    @Test
    void testGeneratePasswordTokenForUser() {
        String token = jwtTokenService.generatePasswordTokenForUser(userModel);
        assertNotNull(token);

        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("testuser", claims.getSubject());
        assertEquals(userModel.getId(), claims.get("id", Long.class));

        String authoritiesString = userModel.getRoles().stream()
                .map(role -> "ROLE_" + new SimpleGrantedAuthority(role.name()).getAuthority())
                .collect(Collectors.joining(","));
        assertEquals(authoritiesString, claims.get("auth"));
    }


    @Test
    void testToAuthentication() {
        String token = jwtTokenService.generatePasswordTokenForUser(userModel);
        UsernamePasswordAuthenticationToken authentication = jwtTokenService.toAuthentication(token);

        assertEquals(userModel.getUsername(), authentication.getName());
        assertTrue(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
