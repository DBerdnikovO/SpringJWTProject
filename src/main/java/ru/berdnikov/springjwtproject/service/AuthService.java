package ru.berdnikov.springjwtproject.service;

import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.AuthTokenDTO;
import ru.berdnikov.springjwtproject.dto.CreateUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.RefreshTokenRequest;

/**
 * @author danilaberdnikov on AuthService.
 * @project SpringJWTProject
 */
public interface AuthService {
    ResponseEntity<?> registration(CreateUserRequestDTO userRequest);

    ResponseEntity<?> login(CreateUserRequestDTO userRequest);

    ResponseEntity<?> refresh(String userToken);
}
