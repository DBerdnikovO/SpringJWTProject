package ru.berdnikov.springjwtproject.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.berdnikov.springjwtproject.model.UserModel;

/**
 * @author danilaberdnikov on JwtTokenService.
 * @project SpringJWTProject
 */
public interface JwtTokenService {
    String generatePasswordTokenForUser(UserModel userModel);

    UsernamePasswordAuthenticationToken toAuthentication(String token);
}
