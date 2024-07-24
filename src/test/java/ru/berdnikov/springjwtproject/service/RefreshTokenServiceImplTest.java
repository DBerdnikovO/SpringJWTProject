package ru.berdnikov.springjwtproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.berdnikov.springjwtproject.config.JWTConfig;
import ru.berdnikov.springjwtproject.exception.RefreshTokenException;
import ru.berdnikov.springjwtproject.model.RefreshToken;
import ru.berdnikov.springjwtproject.repository.RefreshTokenRepository;
import ru.berdnikov.springjwtproject.service.impl.RefreshTokenServiceImpl;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author danilaberdnikov on RefreshTokenServiceImplTest.
 * @project SpringJWTProject
 */
@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    @Mock
    private JWTConfig jwtConfig;

    private RefreshToken refreshToken;

    @BeforeEach
    public void setUp() {
        when(jwtConfig.getRefreshTokenExpiration()).thenReturn(Duration.ofDays(1));

        refreshToken = new RefreshToken(UUID.randomUUID().toString(), 1L, UUID.randomUUID().toString(), Instant.now().plusMillis(jwtConfig.getRefreshTokenExpiration().toMillis()));
    }

    @Test
    void testGenerateRefreshTokenForUser() {
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(refreshToken);

        String refreshTokenValue = refreshTokenService.generateRefreshTokenForUser(1L);

        assertNotNull(refreshTokenValue);
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    void testGetByValue_TokenExists() {
        when(refreshTokenRepository.findByValue(anyString())).thenReturn(Optional.of(refreshToken));

        RefreshToken foundToken = refreshTokenService.getByValue(refreshToken.getValue());

        assertNotNull(foundToken);
        assertEquals(refreshToken.getValue(), foundToken.getValue());
    }

    @Test
    void testGetByValue_TokenDoesNotExist() {
        when(refreshTokenRepository.findByValue(anyString())).thenReturn(Optional.empty());

        assertThrows(RefreshTokenException.class, () -> refreshTokenService.getByValue(refreshToken.getValue()));
    }
}
