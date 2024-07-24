package ru.berdnikov.springjwtproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danilaberdnikov on LoginUserRequestDTO.
 * @project SpringJWTProject
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequestDTO {
    private String email;
    private String password;
}
