package ru.berdnikov.springjwtproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.berdnikov.springjwtproject.dto.LoginUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.RefreshTokenRequestDTO;
import ru.berdnikov.springjwtproject.service.AuthService;

/**
 * @author danilaberdnikov on AuthController.
 * @project SpringJWTProject
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/reg")
    public ResponseEntity registerAndGetToken(@RequestBody RegistrationUserRequestDTO userRequest) {
        return authService.registration(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity loginAndGetToken(@RequestBody RegistrationUserRequestDTO userRequest) {
        return authService.login(userRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody RefreshTokenRequestDTO request) {
        return authService.refresh(request.getRefreshToken());
    }
}
