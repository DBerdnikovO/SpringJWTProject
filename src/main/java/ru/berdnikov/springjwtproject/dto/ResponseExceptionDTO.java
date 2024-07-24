package ru.berdnikov.springjwtproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danilaberdnikov on ResponseExceptionDTO.
 * @project SpringJWTProject
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseExceptionDTO {
    private String httpStatus;
    private String message;
}
