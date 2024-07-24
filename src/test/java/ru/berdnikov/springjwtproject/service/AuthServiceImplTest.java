package ru.berdnikov.springjwtproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.LoginUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.TokenDataDTO;
import ru.berdnikov.springjwtproject.model.RefreshToken;
import ru.berdnikov.springjwtproject.model.RoleType;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.service.impl.AuthServiceImpl;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author danilaberdnikov on AuthServiceImplTest.
 * @project SpringJWTProject
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private ResponseService responseService;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegistrationUserRequestDTO registrationUserRequest;
    private LoginUserRequestDTO loginUserRequest;
    private UserModel userModel;

    @BeforeEach
    public void setUp() {
        registrationUserRequest = new RegistrationUserRequestDTO("testuser", "password", "test@example.com", Set.of("USER"));
        loginUserRequest = new LoginUserRequestDTO("test@example.com", "password");
        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setUsername("testuser");
        userModel.setPassword("password");
        userModel.setRoles(Set.of(RoleType.USER));
    }

    @Test
    void testRegistration() {
        when(userService.saveUser(registrationUserRequest)).thenReturn(userModel);
        TokenDataDTO tokenData = new TokenDataDTO("accessToken", "refreshToken");
        when(jwtTokenService.generatePasswordTokenForUser(userModel)).thenReturn("accessToken");
        when(refreshTokenService.generateRefreshTokenForUser(userModel.getId())).thenReturn("refreshToken");
        when(responseService.success(any())).thenReturn(ResponseEntity.ok(tokenData));

        ResponseEntity<TokenDataDTO> response = authService.registration(registrationUserRequest);

        assertEquals(ResponseEntity.ok(tokenData), response);
        verify(userService, times(1)).saveUser(registrationUserRequest);
    }

    @Test
    void testLogin_Success() {
        when(userService.findUserByEmail(loginUserRequest.getEmail())).thenReturn(userModel);
        when(userService.passwordMatch(loginUserRequest.getPassword(), userModel.getPassword())).thenReturn(true);
        TokenDataDTO tokenData = new TokenDataDTO("accessToken", "refreshToken");
        when(jwtTokenService.generatePasswordTokenForUser(userModel)).thenReturn("accessToken");
        when(refreshTokenService.generateRefreshTokenForUser(userModel.getId())).thenReturn("refreshToken");
        when(responseService.success(any())).thenReturn(ResponseEntity.ok(tokenData));

        ResponseEntity<?> response = authService.login(loginUserRequest);

        assertEquals(ResponseEntity.ok(tokenData), response);
    }

    @Test
    void testLogin_Failure() {
        when(userService.findUserByEmail(loginUserRequest.getEmail())).thenReturn(userModel);
        when(userService.passwordMatch(loginUserRequest.getPassword(), userModel.getPassword())).thenReturn(false);
        when(responseService.passwordError()).thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password"));

        ResponseEntity<?> response = authService.login(loginUserRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Incorrect password", response.getBody());
    }

    @Test
    void testLogin_UserNotFound() {
        when(userService.findUserByEmail(loginUserRequest.getEmail())).thenReturn(null);
        when(responseService.emailError(loginUserRequest.getEmail())).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));

        ResponseEntity<?> response = authService.login(loginUserRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void testRefresh_Success() {
        String refreshTokenValue = "refreshToken";
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), userModel.getId(), refreshTokenValue, Instant.now().plusSeconds(3600));
        when(refreshTokenService.getByValue(refreshTokenValue)).thenReturn(refreshToken);
        when(userService.findUserById(refreshToken.getUserId())).thenReturn(userModel);
        TokenDataDTO tokenData = new TokenDataDTO("accessToken", "newRefreshToken");
        when(jwtTokenService.generatePasswordTokenForUser(userModel)).thenReturn("accessToken");
        when(refreshTokenService.generateRefreshTokenForUser(userModel.getId())).thenReturn("newRefreshToken");
        when(responseService.success(any())).thenReturn(ResponseEntity.ok(tokenData));

        ResponseEntity<?> response = authService.refresh(refreshTokenValue);

        assertEquals(ResponseEntity.ok(tokenData), response);
    }

    @Test
    void testRefresh_TokenExpired() {
        String refreshTokenValue = "refreshToken";
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), userModel.getId(), refreshTokenValue, Instant.now().minusSeconds(3600));
        when(refreshTokenService.getByValue(refreshTokenValue)).thenReturn(refreshToken);
        when(responseService.expiredTokenRefreshError()).thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Expired refresh token"));

        ResponseEntity<?> response = authService.refresh(refreshTokenValue);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Expired refresh token", response.getBody());
    }
}
