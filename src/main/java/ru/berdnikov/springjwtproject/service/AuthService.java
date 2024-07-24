package ru.berdnikov.springjwtproject.service;

import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;

/**
 * @author danilaberdnikov on AuthService.
 * @project SpringJWTProject
 */
public interface AuthService {
    ResponseEntity<?> registration(RegistrationUserRequestDTO userRequest);

    ResponseEntity<?> login(RegistrationUserRequestDTO userRequest);

    ResponseEntity<?> refresh(String userToken);
}
