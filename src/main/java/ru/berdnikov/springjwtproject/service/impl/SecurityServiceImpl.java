package ru.berdnikov.springjwtproject.service.impl;

import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.dto.TokenData;
import ru.berdnikov.springjwtproject.service.SecurityService;

/**
 * @author danilaberdnikov on SecurityServiceImpl.
 * @project SpringJWTProject
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public TokenData processRefreshToken(String refreshTokenValue) {
        return null;
    }
}
