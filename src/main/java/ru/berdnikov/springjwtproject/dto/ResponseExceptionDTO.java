package ru.berdnikov.springjwtproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author danilaberdnikov on ResponseExceptionDTO.
 * @project SpringJWTProject
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseExceptionDTO {
    private String httpStatus;
    private String message;
}
