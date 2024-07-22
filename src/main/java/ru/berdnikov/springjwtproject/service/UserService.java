package ru.berdnikov.springjwtproject.service;

import ru.berdnikov.springjwtproject.model.UserModel;

import java.util.List;
import java.util.Optional;

/**
 * @author danilaberdnikov on UserService.
 * @project SpringJWTProject
 */
public interface UserService {
    Optional<UserModel> findUserByEmail(String email);

    Optional<UserModel> findUserById(Long id);

    List<UserModel> findAll();

    void saveUser(UserModel userModel);
}
