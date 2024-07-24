package ru.berdnikov.springjwtproject.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.service.ValidateTokenService;

import javax.crypto.SecretKey;
import java.security.Key;

/**
 * @author danilaberdnikov on ValidateTokenServiceImpl.
 * @project SpringJWTProject
 */
@Slf4j
@Service
public class ValidateTokenServiceImpl implements ValidateTokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;


    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, getSigningKey());
    }

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
