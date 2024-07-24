package ru.berdnikov.springjwtproject.service.impl;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.berdnikov.springjwtproject.config.JWTConfig;
import ru.berdnikov.springjwtproject.exception.RefreshTokenException;
import ru.berdnikov.springjwtproject.model.RefreshToken;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.repository.RefreshTokenRepository;
import ru.berdnikov.springjwtproject.service.RefreshTokenService;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author danilaberdnikov on RefreshTokenServiceImpl.
 * @project SpringJWTProject
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JWTConfig jwtConfig;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String generateRefreshTokenForUser(Long userId) {
        Date expirationDate = new Date((System.currentTimeMillis() + jwtConfig.getRefreshTokenExpiration().toMillis()));

        String refreshTokenValue = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken(id, userId, refreshTokenValue, expirationDate.toInstant());
        refreshTokenRepository.save(refreshToken);

        return refreshTokenValue;
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken getByValue(String refreshToken) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByValue(refreshToken);
        return optionalRefreshToken.orElseThrow(() -> new RefreshTokenException("Refresh token not found: " + refreshToken));
    }
}
