package ru.berdnikov.springjwtproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.berdnikov.springjwtproject.model.UserModel;

import java.util.Optional;

/**
 * @author danilaberdnikov on UserRepository.
 * @project SpringJWTProject
 */
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findUserModelByEmail(String email);

    boolean existsByEmail(String email);
}
