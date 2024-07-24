package ru.berdnikov.springjwtproject.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.berdnikov.springjwtproject.model.UserModel;

/**
 * @author danilaberdnikov on PasswordTokenService.
 * @project SpringJWTProject
 */
public interface PasswordTokenService {
    String generatePasswordTokenForUser(UserModel userModel);

    UsernamePasswordAuthenticationToken toAuthentication(String token);
}
