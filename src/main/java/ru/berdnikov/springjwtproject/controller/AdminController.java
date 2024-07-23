package ru.berdnikov.springjwtproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author danilaberdnikov on AdminController.
 * @project SpringJWTProject
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok().body("You are ADMIN");
    }
}
