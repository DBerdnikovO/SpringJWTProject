package ru.berdnikov.springjwtproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author danilaberdnikov on AuthTokenDTO.
 * @project SpringJWTProject
 */
@Data
@Builder
@AllArgsConstructor
public class AuthTokenDTO {
    private String token;
}
