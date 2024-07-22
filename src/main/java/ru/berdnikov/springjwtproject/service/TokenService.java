package ru.berdnikov.springjwtproject.service;

import org.springframework.security.core.Authentication;

/**
 * @author danilaberdnikov on TokenService.
 * @project SpringJWTProject
 */
public interface TokenService {
    String generateToken(Authentication authentication, String id);

    Authentication toAuthentication(String token);

    boolean validateToken(String authToken);
}
