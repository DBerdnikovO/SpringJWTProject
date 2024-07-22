package ru.berdnikov.springjwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.dto.AuthTokenDTO;
import ru.berdnikov.springjwtproject.dto.CreateUserRequest;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on AuthServiceImpl.
 * @project SpringJWTProject
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final TokenService tokenService;
    private final UserCustomDetailsService userCustomDetailsService;
    private final ResponseService responseService;

    @Override
    public ResponseEntity<AuthTokenDTO> register(CreateUserRequest userRequest) {
        UserModel userModel = userService.saveUser(userRequest);
        Authentication authentication = authenticateUser(userModel);

        String token = tokenService.generateToken(authentication, String.valueOf(userModel.getId()));
        return responseService.success(token);
    }

    @Override
    public ResponseEntity<?> login(CreateUserRequest userRequest) {
        Optional<AppUser> optionalPerson = Optional.ofNullable(userCustomDetailsService.loadUserByUsername(userRequest.getEmail()));
        return optionalPerson.map(person -> {
            String codePassword = person.getPassword().replaceAll("[\\[\\], ]", "");
            if (Boolean.TRUE.equals(userService.passwordMatch(userRequest.getPassword(), codePassword))) {
                Authentication authentication = authenticateUser(person);

                String token = tokenService.generateToken(authentication, String.valueOf(person.getUserId()));
                return responseService.success(token);
            } else {
                return responseService.passwordError();
            }
        }).orElse(responseService.emailError(userRequest.getEmail()));
    }


    private Authentication authenticateUser(UserModel userModel) {
        AppUser appUser = convertToAppUser(userModel);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                appUser, null, appUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private Authentication authenticateUser(AppUser appUser) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                appUser, null, appUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private AppUser convertToAppUser(UserModel userModel) {
        Set<GrantedAuthority> authorities = userModel.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
        return new AppUser(userModel, authorities);
    }
}
