package ru.berdnikov.springjwtproject.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.berdnikov.springjwtproject.model.UserModel;

/**
 * @author danilaberdnikov on SecurityService.
 * @project SpringJWTProject
 */
public interface SecurityService {

    String generatePasswordTokenForUser(UserModel userModel);

    String generateRefreshTokenForUser(UserModel userModel);

    UsernamePasswordAuthenticationToken toAuthentication(String token);

    boolean validateAccessToken(String accessToken);

    boolean validateRefreshToken(String refreshToken);
}
