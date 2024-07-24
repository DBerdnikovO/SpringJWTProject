package ru.berdnikov.springjwtproject.service;

import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.TokenDataDTO;

/**
 * @author danilaberdnikov on ResponseService.
 * @project SpringJWTProject
 */
public interface ResponseService {

    ResponseEntity<String> expiredTokenRefreshError();

    ResponseEntity<TokenDataDTO> success(TokenDataDTO tokenDataDTO);

    ResponseEntity<String> passwordError();

    ResponseEntity<String> emailError(String email);
}
