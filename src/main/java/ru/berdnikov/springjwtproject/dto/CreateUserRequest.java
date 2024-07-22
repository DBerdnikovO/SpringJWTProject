package ru.berdnikov.springjwtproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author danilaberdnikov on CreateUserRequest.
 * @project SpringJWTProject
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> roles = new HashSet<>();
}
