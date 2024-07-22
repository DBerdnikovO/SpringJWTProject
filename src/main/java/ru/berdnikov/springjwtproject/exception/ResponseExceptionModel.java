package ru.berdnikov.springjwtproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author danilaberdnikov on ResponseExceptionModel.
 * @project SpringJWTProject
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseExceptionModel {
    private String httpStatus;
    private String message;
}
