package ru.berdnikov.springjwtproject.exception;

/**
 * @author danilaberdnikov on RefreshTokenException.
 * @project SpringJWTProject
 */
public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String message) {
        super(message);
    }
}
