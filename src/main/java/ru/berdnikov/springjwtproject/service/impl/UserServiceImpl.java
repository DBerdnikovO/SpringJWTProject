package ru.berdnikov.springjwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.repository.UserRepository;
import ru.berdnikov.springjwtproject.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public void saveUser(UserModel userModel) {
        userRepository.save(userModel);
    }

    @Override
    public UserModel createUser(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(String.valueOf(userModel.getPassword())).toCharArray());
        return userRepository.save(userModel);
    }
}
