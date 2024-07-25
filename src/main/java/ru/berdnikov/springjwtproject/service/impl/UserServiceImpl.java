package ru.berdnikov.springjwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;
import ru.berdnikov.springjwtproject.exception.UserException;
import ru.berdnikov.springjwtproject.model.RoleType;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.repository.UserRepository;
import ru.berdnikov.springjwtproject.service.UserService;
import ru.berdnikov.springjwtproject.util.ErrorCode;

import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on UserServiceImpl.
 * @project SpringJWTProject
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserModel findUserByEmail(String email) {
        return userRepository.findUserModelByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id.toString()));
    }

    @Override
    public UserModel saveUser(RegistrationUserRequestDTO registrationUserRequestDTO) {
        if (Boolean.TRUE.equals(userExist(registrationUserRequestDTO))) {
            throw new UserException(ErrorCode.USER_ALREADY_EXIST.getError());
        } else {
            UserModel user = convertToUserModel(registrationUserRequestDTO);
            return userRepository.save(user);
        }
    }

    @Override
    public Boolean passwordMatch(String inPassword, String codePassword) {
        return passwordEncoder.matches(inPassword, codePassword);
    }

    private Boolean userExist(RegistrationUserRequestDTO userRequest) {
        return userRepository.existsByEmail(userRequest.getEmail());
    }

    private UserModel convertToUserModel(RegistrationUserRequestDTO request) {
        return UserModel.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .roles(request.getRoles().stream()
                        .map(role -> RoleType.valueOf(role.toUpperCase()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
