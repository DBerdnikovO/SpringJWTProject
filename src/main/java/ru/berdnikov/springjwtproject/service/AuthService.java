package ru.berdnikov.springjwtproject.service;

import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.AuthTokenDTO;
import ru.berdnikov.springjwtproject.dto.CreateUserRequest;

/**
 * @author danilaberdnikov on AuthService.
 * @project SpringJWTProject
 */
public interface AuthService {
    ResponseEntity<AuthTokenDTO> register(CreateUserRequest userRequest);

    ResponseEntity<?> login(CreateUserRequest userRequest);
}
