package ru.berdnikov.springjwtproject.service;

import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.AuthTokenDTO;
import ru.berdnikov.springjwtproject.dto.TokenData;

/**
 * @author danilaberdnikov on ResponseService.
 * @project SpringJWTProject
 */
public interface ResponseService {
    ResponseEntity<AuthTokenDTO> error(String error);

    ResponseEntity<TokenData> success(TokenData tokenData);

    ResponseEntity<String> passwordError();

    ResponseEntity<String> emailError(String email);
}
