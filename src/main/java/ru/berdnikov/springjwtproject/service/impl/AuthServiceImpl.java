package ru.berdnikov.springjwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.dto.LoginUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.TokenDataDTO;
import ru.berdnikov.springjwtproject.model.RefreshToken;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.*;

import java.time.Instant;
import java.util.Optional;

/**
 * @author danilaberdnikov on AuthServiceImpl.
 * @project SpringJWTProject
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;
    private final ResponseService responseService;

    @Override
    public ResponseEntity<TokenDataDTO> registration(RegistrationUserRequestDTO userRequest) {
        UserModel userModel = userService.saveUser(userRequest);
        return responseService.success(createTokenData(userModel));
    }

    @Override
    public ResponseEntity<?> login(LoginUserRequestDTO userRequest) {
        UserModel foundUser = userService.findUserByEmail(userRequest.getEmail());

        Optional<UserModel> optionalUserModel = Optional.ofNullable(foundUser);
        return optionalUserModel.map(userModel -> {
            if (Boolean.TRUE.equals(userService.passwordMatch(userRequest.getPassword(), userModel.getPassword()))) {
                TokenDataDTO tokenDataDTO = createTokenData(userModel);
                return responseService.success(tokenDataDTO);
            } else {
                return responseService.passwordError();
            }
        }).orElseGet(() -> responseService.emailError(userRequest.getEmail()));
    }

    @Override
    public ResponseEntity<?> refresh(String userToken) {
        RefreshToken refreshToken = refreshTokenService.getByValue(userToken);

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            return responseService.expiredTokenRefreshError();
        }

        UserModel userModel = userService.findUserById(refreshToken.getUserId());
        TokenDataDTO tokenDataDTO = createTokenData(userModel);
        return responseService.success(tokenDataDTO);
    }

    private TokenDataDTO createTokenData(UserModel userModel) {
        String passwordTokenForUser = jwtTokenService.generatePasswordTokenForUser(userModel);
        String refreshTokenForUser = refreshTokenService.generateRefreshTokenForUser(userModel.getId());
        return new TokenDataDTO(passwordTokenForUser, refreshTokenForUser);
    }
}
