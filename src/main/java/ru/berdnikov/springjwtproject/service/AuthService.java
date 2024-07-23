package ru.berdnikov.springjwtproject.service;

import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.AuthTokenDTO;
import ru.berdnikov.springjwtproject.dto.CreateUserRequestDTO;

/**
 * @author danilaberdnikov on AuthService.
 * @project SpringJWTProject
 */
public interface AuthService {
    ResponseEntity<AuthTokenDTO> registration(CreateUserRequestDTO userRequest);

    ResponseEntity<?> login(CreateUserRequestDTO userRequest);
}
