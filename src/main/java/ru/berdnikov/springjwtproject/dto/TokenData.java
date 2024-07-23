package ru.berdnikov.springjwtproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danilaberdnikov on TokenData.
 * @project SpringJWTProject
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenData {
    private String token;
    private String refreshToken;
}
