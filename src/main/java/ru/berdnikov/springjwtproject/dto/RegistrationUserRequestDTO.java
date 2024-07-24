package ru.berdnikov.springjwtproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Регистрация нвоого пользователя")
public class RegistrationUserRequestDTO {

    @Schema(description = "Ник пользователя", example = "john_doe")
    private String username;

    @Schema(description = "Пароль пользователя", example = "P@ssw0rd123")
    private String password;

    @Schema(description = "Email пользователя", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Роли пользователя", example = "[\"USER\", \"ADMIN\"]")
    private Set<String> roles = new HashSet<>();
}

