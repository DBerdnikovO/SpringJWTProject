package ru.berdnikov.springjwtproject.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.berdnikov.springjwtproject.dto.LoginUserRequestDTO;
import ru.berdnikov.springjwtproject.dto.RegistrationUserRequestDTO;
import ru.berdnikov.springjwtproject.service.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author danilaberdnikov on AuthController.
 * @project SpringJWTProject
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testRegisterAndGetToken() throws Exception {
        when(authService.registration(any(RegistrationUserRequestDTO.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        String jsonRequest = "{\"username\":\"test\",\"password\":\"password\"}";

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginAndGetToken() throws Exception {
        when(authService.login(any(LoginUserRequestDTO.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        String jsonRequest = "{\"email\":\"test@example.com\",\"password\":\"password\"}";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testRefresh() throws Exception {
        when(authService.refresh(any(String.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        String jsonRequest = "{\"refreshToken\":\"someToken\"}";

        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }
}
