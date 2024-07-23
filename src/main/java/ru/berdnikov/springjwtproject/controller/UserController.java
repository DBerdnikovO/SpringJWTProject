package ru.berdnikov.springjwtproject.controller;

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
public class UserController {

    @GetMapping()
    public ResponseEntity<String> user() {
        return ResponseEntity.ok().body("You are USER");
    }
}
