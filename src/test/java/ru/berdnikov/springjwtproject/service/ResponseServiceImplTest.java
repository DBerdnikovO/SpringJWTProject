package ru.berdnikov.springjwtproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.berdnikov.springjwtproject.dto.TokenDataDTO;
import ru.berdnikov.springjwtproject.service.impl.ResponseServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author danilaberdnikov on ResponseServiceImplTest.
 * @project SpringJWTProject
 */
@ExtendWith(MockitoExtension.class)
public class ResponseServiceImplTest {

    @InjectMocks
    private ResponseServiceImpl responseService;

    @Test
    public void testExpiredTokenRefreshError() {
        ResponseEntity<String> response = responseService.expiredTokenRefreshError();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Expired refresh token", response.getBody());
    }

    @Test
    public void testSuccess() {
        TokenDataDTO tokenDataDTO = new TokenDataDTO("accessToken", "refreshToken");
        ResponseEntity<TokenDataDTO> response = responseService.success(tokenDataDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tokenDataDTO, response.getBody());
    }

    @Test
    public void testPasswordError() {
        ResponseEntity<String> response = responseService.passwordError();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Incorrect password", response.getBody());
    }

    @Test
    public void testEmailError() {
        ResponseEntity<String> response = responseService.emailError("test@example.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found: test@example.com", response.getBody());
    }
}
