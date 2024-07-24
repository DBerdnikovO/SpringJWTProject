package ru.berdnikov.springjwtproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danilaberdnikov on TokenDataDTO.
 * @project SpringJWTProject
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Токены")
public class TokenDataDTO {

    @Schema(description = "Токен", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1bXBhIiwiYXV0aCI6IlJPTEVfVVNFUiIsImlkIjoyMywiaWF0IjoxNzIxODExNTAwLCJleHAiOjE3MjE4MTE4MDB9.mq-Q77ZNC_YT37iMVeiWCOSI4nMk_2K7GX2PDkRWx6lyVTjsH5GzJIPrrGfJvUo0ua5-D4R9I0jnIxc_VPRUNg")
    private String token;

    @Schema(description = "Рефреш токен", example = "05f8b0ec-1244-4c7a-9a24-c705fe3fe8ca")
    private String refreshToken;
}
