package ru.berdnikov.springjwtproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.berdnikov.springjwtproject.service.SecurityService;

/**
 * @author danilaberdnikov on TokenController.
 * @project SpringJWTProject
 */
@RestController
@RequiredArgsConstructor
public class TokenController {
    private final SecurityService securityService;
}
