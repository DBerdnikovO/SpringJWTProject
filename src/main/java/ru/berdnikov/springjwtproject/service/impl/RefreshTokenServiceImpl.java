package ru.berdnikov.springjwtproject.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.berdnikov.springjwtproject.model.RefreshToken;
import ru.berdnikov.springjwtproject.repository.RefreshTokenRepository;
import ru.berdnikov.springjwtproject.service.RefreshTokenService;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * @author danilaberdnikov on RefreshTokenServiceImpl.
 * @project SpringJWTProject
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void save(Long userId) {
        Date expirationDate = new Date((System.currentTimeMillis() + refreshTokenExpiration.toMillis()));

        String refreshTokenValue = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken(id, userId, refreshTokenValue, expirationDate.toInstant());
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken getByValue(String refreshToken) {
        return null;
    }
}
