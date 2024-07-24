package ru.berdnikov.springjwtproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Логинг пользователя")
public class LoginUserRequestDTO {

    @Schema(description = "Email пользователя", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Пароль пользователя", example = "P@ssw0rd123")
    private String password;
}

