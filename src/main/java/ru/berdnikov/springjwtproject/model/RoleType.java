package ru.berdnikov.springjwtproject.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author danilaberdnikov on RoleType.
 * @project SpringJWTProject
 */
@RequiredArgsConstructor
public enum RoleType implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}

