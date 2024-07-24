package ru.berdnikov.springjwtproject.util;

/**
 * @author danilaberdnikov on ErrorCode.
 * @project SpringJWTProject
 */
public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    INCORRECT_PASSWORD("Incorrect password"),
    INCORRECT_REFRESHTOKEN("Incorrect refresh token"),
    EXPIRED_REFRESHTOKEN("Expired refresh token"),
    USER_ALREADY_EXIST("User already exist");

    private final String error;

    ErrorCode(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
