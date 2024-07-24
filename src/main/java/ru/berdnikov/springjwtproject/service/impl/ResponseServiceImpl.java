package ru.berdnikov.springjwtproject.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.dto.TokenDataDTO;
import ru.berdnikov.springjwtproject.service.ResponseService;
import ru.berdnikov.springjwtproject.util.ErrorCode;

/**
 * @author danilaberdnikov on ResponseServiceImpl.
 * @project SpringJWTProject
 */
@Slf4j
@Service
public class ResponseServiceImpl implements ResponseService {
    @Override
    public ResponseEntity<String> expiredTokenRefreshError() {
        log.warn("Expired refresh token error");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorCode.EXPIRED_REFRESHTOKEN.getError());
    }

    @Override
    public ResponseEntity<TokenDataDTO> success(TokenDataDTO tokenDataDTO) {
        log.info("Token generation success");
        return ResponseEntity.ok(tokenDataDTO);
    }

    @Override
    public ResponseEntity<String> passwordError() {
        log.warn("Incorrect password error");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorCode.INCORRECT_PASSWORD.getError());
    }

    @Override
    public ResponseEntity<String> emailError(String email) {
        log.warn("User not found error for email: {}", email);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("%s: %s", ErrorCode.USER_NOT_FOUND.getError(), email));
    }
}
