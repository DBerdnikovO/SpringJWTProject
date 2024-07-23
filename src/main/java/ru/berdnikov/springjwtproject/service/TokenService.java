package ru.berdnikov.springjwtproject.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * @author danilaberdnikov on TokenService.
 * @project SpringJWTProject
 */
public interface TokenService {

    String generateToken(String username, Long id, List<String> roles);

    String generateToken(String username, Long id, Collection<? extends GrantedAuthority> authorities);

    UsernamePasswordAuthenticationToken toAuthentication(String token);

    boolean validateAccessToken(String accessToken);
}
