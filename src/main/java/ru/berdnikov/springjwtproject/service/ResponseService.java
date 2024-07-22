package ru.berdnikov.springjwtproject.service;

import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.AuthTokenDTO;

/**
 * @author danilaberdnikov on ResponseService.
 * @project SpringJWTProject
 */
public interface ResponseService {
    ResponseEntity<AuthTokenDTO> error(String error);

    ResponseEntity<AuthTokenDTO> success(String token);

    ResponseEntity<String> passwordError();

    ResponseEntity<String> emailError(String email);
}
