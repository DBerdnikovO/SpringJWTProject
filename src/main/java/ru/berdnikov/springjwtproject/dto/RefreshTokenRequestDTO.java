package ru.berdnikov.springjwtproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Обновленный токен")
public class RefreshTokenRequestDTO {

    @Schema(description = "Обновленный токен", example = "6934b1fc-fdb4-4618-9f81-a9519dd8e542")
    private String refreshToken;
}
