package ru.berdnikov.springjwtproject.service;

import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.LoginUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;

/**
 * @author danilaberdnikov on AuthService.
 * @project SpringJWTProject
 */
public interface AuthService {
    ResponseEntity<?> registration(RegistrationUserRequestDTO registrationUserRequestDTO);

    ResponseEntity<?> login(LoginUserRequestDTO loginUserRequestDTO);

    ResponseEntity<?> refresh(String refreshToken);
}
