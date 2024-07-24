package ru.berdnikov.springjwtproject.service;

/**
 * @author danilaberdnikov on ValidateTokenService.
 * @project SpringJWTProject
 */
public interface ValidateTokenService {
    boolean validateToken(String token);
}
