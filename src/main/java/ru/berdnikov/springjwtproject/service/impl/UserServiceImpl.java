package ru.berdnikov.springjwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.berdnikov.springjwtproject.dto.CreateUserRequestDTO;
import ru.berdnikov.springjwtproject.exception.UserException;
import ru.berdnikov.springjwtproject.model.RoleType;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.repository.UserRepository;
import ru.berdnikov.springjwtproject.service.UserService;
import ru.berdnikov.springjwtproject.util.ErrorCode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on UserServiceImpl.
 * @project SpringJWTProject
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserModel> findUserByEmail(String email) {
        return userRepository.findUserModelByEmail(email);
    }

    @Override
    public Optional<UserModel> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserModel saveUser(CreateUserRequestDTO createUserRequestDTO) {
        if (Boolean.TRUE.equals(personExist(createUserRequestDTO))) {
            throw new UserException(ErrorCode.PERSON_ALREADY_EXIST.getError());
        } else {
            UserModel user = convertToUserModel(createUserRequestDTO);
            return userRepository.save(user);
        }
    }

    private Boolean personExist(CreateUserRequestDTO userRequest) {
        return userRepository.existsByEmail(userRequest.getEmail());
    }

    @Override
    public Boolean passwordMatch(String inPassword, String codePassword) {
        return passwordEncoder.matches(inPassword, codePassword);
    }

    private UserModel convertToUserModel(CreateUserRequestDTO request) {
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
