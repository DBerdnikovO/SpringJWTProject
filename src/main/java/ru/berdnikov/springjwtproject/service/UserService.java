package ru.berdnikov.springjwtproject.service;

import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;
import ru.berdnikov.springjwtproject.model.UserModel;

/**
 * @author danilaberdnikov on UserService.
 * @project SpringJWTProject
 */
public interface UserService {
    UserModel findUserByEmail(String email);

    UserModel findUserById(Long id);

    UserModel saveUser(RegistrationUserRequestDTO registrationUserRequestDTO);

    Boolean passwordMatch(String inPassword, String codePassword);
}
