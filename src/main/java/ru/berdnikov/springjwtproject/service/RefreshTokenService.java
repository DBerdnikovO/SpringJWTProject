package ru.berdnikov.springjwtproject.service;

import ru.berdnikov.springjwtproject.model.RefreshToken;
import ru.berdnikov.springjwtproject.model.UserModel;

/**
 * @author danilaberdnikov on RefreshTokenService.
 * @project SpringJWTProject
 */
public interface RefreshTokenService {
    void save(Long userId);

    RefreshToken getByValue(String refreshToken);

    String generateRefreshTokenForUser(UserModel userModel);
}
