package ru.berdnikov.springjwtproject.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.berdnikov.springjwtproject.service.impl.ValidateTokenServiceImpl;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author danilaberdnikov on ValidateTokenServiceImplTest.
 * @project SpringJWTProject
 */
@ExtendWith(MockitoExtension.class)
class ValidateTokenServiceImplTest {

    @InjectMocks
    private ValidateTokenServiceImpl validateTokenService;

    private SecretKey secretKey;

    @BeforeEach
    public void setUp() {
        String jwtSecret = "g+xFdzCt/aevgeZwSGYplPoEk3aItmPcg38OkEqQ3NGJ+nT3tqnM03DH/KwIS1563pk4+HzbzCaUnCUiOyQewQ==";
        ReflectionTestUtils.setField(validateTokenService, "jwtSecret", jwtSecret);
        secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Test
    void testValidateToken_ValidToken() {
        String token = Jwts.builder()
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(secretKey)
                .compact();

        assertTrue(validateTokenService.validateToken(token));
    }

    @Test
    void testValidateToken_ExpiredToken() {
        String token = Jwts.builder()
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(secretKey)
                .compact();

        assertFalse(validateTokenService.validateToken(token));
    }

    @Test
    void testValidateToken_MalformedToken() {
        String token = "malformed.token";

        assertFalse(validateTokenService.validateToken(token));
    }

    @Test
    void testValidateToken_InvalidSignatureToken() {
        SecretKey anotherKey = Keys.hmacShaKeyFor("another-secret".getBytes());
        String token = Jwts.builder()
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(anotherKey)
                .compact();

        assertFalse(validateTokenService.validateToken(token));
    }
}
