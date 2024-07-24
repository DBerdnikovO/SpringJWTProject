package ru.berdnikov.springjwtproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Операции для админов")
public class AdminController {

    @GetMapping
    @Operation(
            summary = "Получить сообщение для админа",
            description = "Позволяет получить сообщение для пользователей с ролью ADMIN",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("You are ADMIN");
    }
}
