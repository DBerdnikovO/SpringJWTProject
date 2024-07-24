package ru.berdnikov.springjwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.dto.CreateUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.TokenData;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.*;

import java.util.Optional;

/**
 * @author danilaberdnikov on AuthServiceImpl.
 * @project SpringJWTProject
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordTokenService passwordTokenService;
    private final RefreshTokenService refreshTokenService;
    private final ResponseService responseService;

    @Override
    public ResponseEntity<TokenData> registration(CreateUserRequestDTO userRequest) {
        UserModel userModel = userService.saveUser(userRequest);
        String passwordTokenForUser = passwordTokenService.generatePasswordTokenForUser(userModel);
        String refreshTokenForUser = refreshTokenService.generateRefreshTokenForUser(userModel);
        return responseService.success(passwordTokenForUser, refreshTokenForUser);
    }

    @Override
    public ResponseEntity<?> login(CreateUserRequestDTO userRequest) {
        Optional<UserModel> optionalUserModel = Optional.ofNullable(userService.findUserByEmail(userRequest.getEmail()));
        return optionalUserModel.map(userModel -> {
            String password = userModel.getPassword().replaceAll("[\\[\\], ]", "");
            if (Boolean.TRUE.equals(userService.passwordMatch(userRequest.getPassword(), password))) {
                String passwordTokenForUser = passwordTokenService.generatePasswordTokenForUser(userModel);
                String refreshTokenForUser = refreshTokenService.generateRefreshTokenForUser(userModel);
                return responseService.success(passwordTokenForUser, refreshTokenForUser);
            } else {
                return responseService.passwordError();
            }
        }).orElse(responseService.emailError(userRequest.getEmail()));
    }

    @Override
    public ResponseEntity<?> refresh(String userToken) {
        return null;
    }
}
