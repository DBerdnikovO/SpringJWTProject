package ru.berdnikov.springjwtproject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author danilaberdnikov on AuthenticationExceptionHandler.
 * @project SpringJWTProject
 */
@Slf4j
@ControllerAdvice
public class AuthenticationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ResponseExceptionModel> handleAuthenticationException(AuthenticationException ex) {
        ResponseExceptionModel responseExceptionModel = new ResponseExceptionModel(
                HttpStatus.UNAUTHORIZED.toString(),
                "Authentication failed: " + ex.getMessage()
        );
        log.error("AuthenticationException: ", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseExceptionModel);
    }
}
