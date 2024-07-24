package ru.berdnikov.springjwtproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;
import ru.berdnikov.springjwtproject.exception.UserException;
import ru.berdnikov.springjwtproject.model.RoleType;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.repository.UserRepository;
import ru.berdnikov.springjwtproject.service.impl.UserServiceImpl;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author danilaberdnikov on UserServiceImplTest.
 * @project SpringJWTProject
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private RegistrationUserRequestDTO registrationUserRequestDTO;
    private UserModel userModel;

    @BeforeEach
    public void setUp() {
        registrationUserRequestDTO = new RegistrationUserRequestDTO("username", "password", "email@example.com", Set.of("USER"));
        userModel = UserModel.builder()
                .id(1L)
                .username("username")
                .password("encodedPassword")
                .email("email@example.com")
                .roles(Set.of(RoleType.USER))
                .build();
    }

    @Test
    void testFindUserByEmail_UserExists() {
        when(userRepository.findUserModelByEmail("email@example.com")).thenReturn(Optional.of(userModel));

        UserModel foundUser = userService.findUserByEmail("email@example.com");

        assertNotNull(foundUser);
        assertEquals("username", foundUser.getUsername());
    }

    @Test
    void testFindUserByEmail_UserDoesNotExist() {
        when(userRepository.findUserModelByEmail("email@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.findUserByEmail("email@example.com"));
    }

    @Test
    void testFindUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));

        UserModel foundUser = userService.findUserById(1L);

        assertNotNull(foundUser);
        assertEquals("username", foundUser.getUsername());
    }

    @Test
    void testFindUserById_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.findUserById(1L));
    }

    @Test
    void testSaveUser_UserDoesNotExist() {
        when(userRepository.existsByEmail("email@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        UserModel savedUser = userService.saveUser(registrationUserRequestDTO);

        assertNotNull(savedUser);
        assertEquals("username", savedUser.getUsername());
        assertEquals("email@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    void testSaveUser_UserAlreadyExists() {
        when(userRepository.existsByEmail("email@example.com")).thenReturn(true);

        assertThrows(UserException.class, () -> userService.saveUser(registrationUserRequestDTO));
    }

    @Test
    void testPasswordMatch() {
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        assertTrue(userService.passwordMatch("password", "encodedPassword"));
    }
}
