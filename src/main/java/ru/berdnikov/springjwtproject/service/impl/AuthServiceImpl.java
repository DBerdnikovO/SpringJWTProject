package ru.berdnikov.springjwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.dto.AuthTokenDTO;
import ru.berdnikov.springjwtproject.dto.CreateUserRequestDTO;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.*;

import java.util.Optional;

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
    public ResponseEntity<AuthTokenDTO> registration(CreateUserRequestDTO userRequest) {
        UserModel userModel = userService.saveUser(userRequest);
        String token = tokenService.generateToken(
                userModel.getUsername(),
                userModel.getId(),
                userModel.getRoles().stream().map(Enum::name).toList());
        return responseService.success(token);
    }

    @Override
    public ResponseEntity<?> login(CreateUserRequestDTO userRequest) {
        Optional<AppUser> optionalPerson = Optional.ofNullable(userCustomDetailsService.loadUserByUsername(userRequest.getEmail()));
        return optionalPerson.map(person -> {
            String codePassword = person.getPassword().replaceAll("[\\[\\], ]", "");
            if (Boolean.TRUE.equals(userService.passwordMatch(userRequest.getPassword(), codePassword))) {
                String token = tokenService.generateToken(person.getUsername(), person.getUserId(), person.getAuthorities());
                return responseService.success(token);
            } else {
                return responseService.passwordError();
            }
        }).orElse(responseService.emailError(userRequest.getEmail()));
    }
}
