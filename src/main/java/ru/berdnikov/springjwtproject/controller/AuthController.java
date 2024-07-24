package ru.berdnikov.springjwtproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.berdnikov.springjwtproject.dto.LoginUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.RefreshTokenRequestDTO;
import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;
import ru.berdnikov.springjwtproject.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Аутентификация и авторизация")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Зарегистрировать пользователя и получить токены")
    public ResponseEntity registerAndGetToken(
            @RequestBody @Parameter(description = "Регистрация пользователя") RegistrationUserRequestDTO registrationUserRequestDTO) {
        return authService.registration(registrationUserRequestDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "Залогинить пользователя и получить токены")
    public ResponseEntity loginAndGetToken(
            @RequestBody @Parameter(description = "Логин по почте и паролю") LoginUserRequestDTO loginUserRequestDTO) {
        return authService.login(loginUserRequestDTO);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновление токена")
    public ResponseEntity refresh(
            @RequestBody @Parameter(description = "Обновление токена") RefreshTokenRequestDTO request) {
        return authService.refresh(request.getRefreshToken());
    }
}
