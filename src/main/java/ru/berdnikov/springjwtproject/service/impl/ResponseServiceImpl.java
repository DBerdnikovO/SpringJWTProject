package ru.berdnikov.springjwtproject.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.dto.AuthTokenDTO;
import ru.berdnikov.springjwtproject.dto.TokenDataDTO;
import ru.berdnikov.springjwtproject.service.ResponseService;
import ru.berdnikov.springjwtproject.util.ErrorCode;

/**
 * @author danilaberdnikov on ResponseServiceImpl.
 * @project SpringJWTProject
 */
@Service
public class ResponseServiceImpl implements ResponseService {
    @Override
    public ResponseEntity<AuthTokenDTO> error(String error) {
        return ResponseEntity.badRequest().body(new AuthTokenDTO(error));
    }

    @Override
    public ResponseEntity<String> expiredTokenRefreshError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorCode.EXPIRED_REFRESHTOKEN.getError());
    }

    @Override
    public ResponseEntity<TokenDataDTO> success(TokenDataDTO tokenDataDTO) {
        return ResponseEntity.ok(tokenDataDTO);
    }

    @Override
    public ResponseEntity<String> passwordError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorCode.INCORRECT_PASSWORD.getError());
    }

    @Override
    public ResponseEntity<String> emailError(String email) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorCode.USER_NOT_FOUND.getError());
    }
}
