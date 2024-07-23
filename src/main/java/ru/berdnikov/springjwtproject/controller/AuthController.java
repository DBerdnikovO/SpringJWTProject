package ru.berdnikov.springjwtproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.berdnikov.springjwtproject.dto.CreateUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.RefreshTokenRequest;
import ru.berdnikov.springjwtproject.dto.TokenData;
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
    public ResponseEntity registerAndGetToken(@RequestBody CreateUserRequestDTO userRequest) {
        return ResponseEntity.ok(authService.registration(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity loginAndGetToken(@RequestBody CreateUserRequestDTO userRequest) {
        return ResponseEntity.ok(authService.login(userRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody RefreshTokenRequest request) {
        final TokenData token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);    }

}
