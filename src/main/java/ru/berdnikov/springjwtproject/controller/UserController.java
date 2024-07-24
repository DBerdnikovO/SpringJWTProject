package ru.berdnikov.springjwtproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author danilaberdnikov on UserController.
 * @project SpringJWTProject
 */
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/user")
@Tag(name = "User", description = "Операции для пользователей")
public class UserController {

    @GetMapping
    @Operation(
            summary = "Получить сообщение для пользователя",
            description = "Позволяет получить сообщение для пользователей с ролью USER",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("You are USER");
    }
}
