package ru.berdnikov.springjwtproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author danilaberdnikov on AuthController.
 * @project SpringJWTProject
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
//    private final User
//
//    @PostMapping("/reg")
//    public ResponseEntity registerAndGetToken(@Validated @RequestBody UserDTO userDTO) {
//        boolean exist = userService.findUserByEmail(userDTO.getEmail()).isPresent();
//        if(exist) {
//            return getBadRequestResponse("User with this email already exists");
//        } else {
//            userService.saveUser(generateUser(userDTO));
//            String token = jwtHelper.generateToken(userDTO.getEmail());
//            return getSuccessfulResponse(token);
//        }
//    }
}
