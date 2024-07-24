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
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

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
        lenient().when(jwtConfig.getSigningKey()).thenReturn((SecretKey) signingKey);
        lenient().when(jwtConfig.getTokenExpiration()).thenReturn(Duration.ofHours(1));

        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setUsername("testuser");
        userModel.setPassword("encryptedPassword");
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

    @Test
    void testValidateToken() {
        String token = jwtTokenService.generatePasswordTokenForUser(userModel);
        assertTrue(jwtTokenService.validateToken(token));

        // Test with expired token
        // Using lenient() to avoid unnecessary stubbing exception
        lenient().when(jwtConfig.getTokenExpiration()).thenReturn(Duration.ofMillis(-1));
        String expiredToken = jwtTokenService.generatePasswordTokenForUser(userModel);
        assertFalse(jwtTokenService.validateToken(expiredToken));

        // Test with malformed token
        String malformedToken = token + "malformed";
        assertFalse(jwtTokenService.validateToken(malformedToken));

        // Test with unsupported token (wrong algorithm)
        String unsupportedToken = Jwts.builder()
                .setSubject(userModel.getUsername())
                .signWith(signingKey, SignatureAlgorithm.HS384)
                .compact();
        assertFalse(jwtTokenService.validateToken(unsupportedToken));
    }

    @Test
    void testInvalidSignatureToken() {
        Key differentSigningKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String tokenWithDifferentKey = Jwts.builder()
                .setSubject(userModel.getUsername())
                .claim("auth", "ROLE_USER")
                .claim("id", userModel.getId())
                .signWith(differentSigningKey, SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();
        assertFalse(jwtTokenService.validateToken(tokenWithDifferentKey));
    }
}
