package org.maciejklonicki.passwordapigenerator.password.controller;

import org.junit.jupiter.api.Test;
import org.maciejklonicki.passwordapigenerator.password.Password;
import org.maciejklonicki.passwordapigenerator.password.PasswordController;
import org.maciejklonicki.passwordapigenerator.password.PasswordRepository;
import org.maciejklonicki.passwordapigenerator.password.PasswordService;
import org.maciejklonicki.passwordapigenerator.password.dto.NewPasswordSingleDto;
import org.maciejklonicki.passwordapigenerator.password.dto.PasswordListDto;
import org.maciejklonicki.passwordapigenerator.password.dto.PasswordSingleDto;
import org.maciejklonicki.passwordapigenerator.password.mapper.NewPasswordSingleDtoMapper;
import org.maciejklonicki.passwordapigenerator.password.mapper.PasswordListDtoMapper;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class PasswordControllerTest {

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private PasswordController passwordController;

    @Mock
    private PasswordService passwordService;



    @Test
    public void getSinglePassword_withExistingPassword_returnsCorrectPasswordSingleDto() {
        Password password = new Password();
        password.setPassword("password");
        password.setComplexity("");
        password.setDateOfPasswordCreation();
        passwordRepository.save(password);

        List<PasswordSingleDto> result = passwordController.getSinglePassword("password");

        assertEquals(1, result.size());
    }

    @Test
    public void getSinglePassword_withNonExistentPassword_returnsEmptyList() {
        String password = "someOfMyPass";

        List<PasswordSingleDto> result = passwordController.getSinglePassword(password);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testIfCanGetAllPasswords() {
        Password password = new Password();
        password.setPassword("asd");
        password.setComplexity("");
        password.setDateOfPasswordCreation();

        Password password1 = new Password();
        password1.setPassword("asd");
        password1.setComplexity("");
        password1.setDateOfPasswordCreation();

        passwordRepository.save(password);
        passwordRepository.save(password1);

        List<PasswordListDto> expectedResult = Arrays.asList(
                new PasswordListDto(password.getId(), password.getPassword(), password.getComplexity()),
                new PasswordListDto(password1.getId(), password1.getPassword(), password1.getComplexity())
        );

        List<PasswordListDto> result = passwordController.getAllPasswords();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetNewSinglePassword() {
        String password = "password1";
        Password expectedPassword = new Password(1L, Instant.now(), password, null);
        when(passwordService.getNewSinglePassword(password)).thenReturn(Optional.of(expectedPassword));

        List<NewPasswordSingleDto> result = passwordController.getNewSinglePassword(password);

        assertThat(result).isEqualTo(NewPasswordSingleDtoMapper.mapToNewPasswordSingleDto(Optional.of(expectedPassword)));
    }
}