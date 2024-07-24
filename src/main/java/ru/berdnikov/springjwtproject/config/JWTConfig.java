package ru.berdnikov.springjwtproject.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.time.Duration;

/**
 * @author danilaberdnikov on JWTConfig.
 * @project SpringJWTProject
 */
@Getter
@Configuration
public class JWTConfig {
    @Value("${jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.tokenExpiration}")
    private Duration tokenExpiration;

    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
