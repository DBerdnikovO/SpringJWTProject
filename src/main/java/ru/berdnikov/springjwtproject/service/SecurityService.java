package ru.berdnikov.springjwtproject.service;

import reactor.core.publisher.Mono;
import ru.berdnikov.springjwtproject.dto.TokenData;

/**
 * @author danilaberdnikov on SecurityService.
 * @project SpringJWTProject
 */
public interface SecurityService {
    TokenData processRefreshToken(String refreshTokenValue) ;
}
