package ru.berdnikov.springjwtproject.service;

import java.security.Key;

/**
 * @author danilaberdnikov on ValidateTokenService.
 * @project SpringJWTProject
 */
public interface ValidateTokenService {
    boolean validateToken(String token);
}
