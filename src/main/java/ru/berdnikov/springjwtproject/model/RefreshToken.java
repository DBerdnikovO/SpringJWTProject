package ru.berdnikov.springjwtproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author danilaberdnikov on RefreshToken.
 * @project SpringJWTProject
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {
    private String id;
    private String userId;
    private String value;
}
