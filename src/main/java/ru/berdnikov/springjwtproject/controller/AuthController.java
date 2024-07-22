package ru.berdnikov.springjwtproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.berdnikov.springjwtproject.dto.CreateUserRequest;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.model.RoleType;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.TokenService;
import ru.berdnikov.springjwtproject.service.UserService;
import ru.berdnikov.springjwtproject.service.impl.UserDetailServiceImpl;

import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on AuthController.
 * @project SpringJWTProject
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/reg")
    public ResponseEntity registerAndGetToken(@RequestBody CreateUserRequest request) {
        boolean exist = userService.findUserByEmail(request.getEmail()).isPresent();
        if (exist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        UserModel userModel = userService.createUser(generateUserFromRequest(request));

        UserDetails appUser = userDetailsService.loadUserByUsername(userModel.getUsername());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                appUser,
                null,
                appUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenService.generateToken(authentication, String.valueOf(userModel.getId()));
        return ResponseEntity.ok(token);
    }

    private UserModel generateUserFromRequest(CreateUserRequest request) {
        return UserModel.builder()
                .email(request.getEmail())
                .password(request.getPassword().toCharArray())
                .username(request.getUsername())
                .roles(request.getRoles().stream()
                        .map(role -> RoleType.valueOf(role.toUpperCase()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
