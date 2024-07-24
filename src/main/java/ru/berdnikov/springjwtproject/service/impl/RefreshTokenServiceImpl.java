package ru.berdnikov.springjwtproject.service.impl;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.config.JWTConfig;
import ru.berdnikov.springjwtproject.model.RefreshToken;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.repository.RefreshTokenRepository;
import ru.berdnikov.springjwtproject.service.RefreshTokenService;

import java.util.Date;
import java.util.UUID;

/**
 * @author danilaberdnikov on RefreshTokenServiceImpl.
 * @project SpringJWTProject
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JWTConfig jwtConfig;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void save(Long userId) {
        Date expirationDate = new Date((System.currentTimeMillis() + jwtConfig.getRefreshTokenExpiration().toMillis()));

        String refreshTokenValue = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken(id, userId, refreshTokenValue, expirationDate.toInstant());
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public String generateRefreshTokenForUser(UserModel userModel) {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtConfig.getRefreshTokenExpiration().toMillis());

        String token = Jwts.builder()
                .setSubject(userModel.getUsername())
                .setExpiration(expirationDate)
                .signWith(jwtConfig.getSigningKey())
                .compact();

        save(userModel.getId());

        return token;
    }

    @Override
    public RefreshToken getByValue(String refreshToken) {
        return null;
    }
}
