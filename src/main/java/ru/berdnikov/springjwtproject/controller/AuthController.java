package ru.berdnikov.springjwtproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.berdnikov.springjwtproject.dto.CreateUserRequest;
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
    public ResponseEntity registerAndGetToken(@RequestBody CreateUserRequest userRequest) {
        return ResponseEntity.ok(authService.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity loginAndGetToken(@RequestBody CreateUserRequest userRequest) {
        return ResponseEntity.ok(authService.login(userRequest));
    }
}
