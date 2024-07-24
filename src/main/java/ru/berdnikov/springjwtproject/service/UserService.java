package ru.berdnikov.springjwtproject.service;

import ru.berdnikov.springjwtproject.dto.CreateUserRequestDTO;
import ru.berdnikov.springjwtproject.model.UserModel;

import java.util.List;
import java.util.Optional;

/**
 * @author danilaberdnikov on UserService.
 * @project SpringJWTProject
 */
public interface UserService {
    UserModel findUserByEmail(String email);

    UserModel findUserById(Long id);

    UserModel saveUser(CreateUserRequestDTO createUserRequestDTO);

    Boolean passwordMatch(String inPassword, String codePassword);
}
