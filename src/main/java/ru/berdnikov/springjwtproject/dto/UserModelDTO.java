package ru.berdnikov.springjwtproject.dto;

import lombok.Data;

/**
 * @author danilaberdnikov on UserModelDTO.
 * @project SpringJWTProject
 */
@Data
public class UserModelDTO {
    private String username;
    private String email;
    private String password;
}
