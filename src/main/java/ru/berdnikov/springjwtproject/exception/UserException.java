package ru.berdnikov.springjwtproject.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author danilaberdnikov on UserException.
 * @project SpringJWTProject
 */
public class UserException extends AuthenticationException {
    public UserException(String message) {
        super(message);
    }
}
