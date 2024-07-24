package ru.berdnikov.springjwtproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.berdnikov.springjwtproject.model.RefreshToken;

import java.util.Optional;

/**
 * @author danilaberdnikov on RefreshTokenRepository.
 * @project SpringJWTProject
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByValue(String value);
}
