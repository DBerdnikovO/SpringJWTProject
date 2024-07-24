package ru.berdnikov.springjwtproject.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * @author danilaberdnikov on OpenApiConfig.
 * @project SpringJWTProject
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Jwt simple Api",
                description = "Веб-приложение с использованием Spring Security и JWT для аутентификации и авторизации пользователей", version = "0.0.1",
                contact = @Contact(
                        name = "Berdnikov Danila"
                )
        )
)
public class OpenApiConfig {

}
