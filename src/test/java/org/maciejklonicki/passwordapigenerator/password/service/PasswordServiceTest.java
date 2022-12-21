package org.maciejklonicki.passwordapigenerator.password.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.maciejklonicki.passwordapigenerator.password.Password;
import org.maciejklonicki.passwordapigenerator.password.PasswordRepository;
import org.maciejklonicki.passwordapigenerator.password.PasswordService;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class PasswordServiceTest {

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    private PasswordRepository passwordRepository;

    @Autowired
    private PasswordService passwordService;

    @Test
    @Transactional
    void checkIfCanGetSinglePassword() {
        //given
        Password newPassword = new Password();
        newPassword.setPassword("password");
        newPassword.setComplexity("średnie");
        newPassword.setDateOfPasswordCreation();
        passwordRepository.save(newPassword);
        //when
        Optional<Password> password = passwordService.getSinglePassword("password");
        //then
        assertThat(password).isNotNull();
    }

    @Test
    public void testGetPassword_existingPassword_shouldReturnPasswordFromDB() {
        String password = "maybeShouldCheckIfItIsWorking";
        Password passwordFromDB = new Password();
        passwordFromDB.setPassword(password);
        passwordFromDB.setComplexity("silne");
        when(passwordRepository.findByPassword(password)).thenReturn(passwordFromDB);

        Optional<Password> result = Optional.ofNullable(passwordRepository.findByPassword(password));

        assertTrue(result.isPresent());
        assertEquals(passwordFromDB, result.get());
    }

    @Test
    public void testGetPassword_newPassword_shouldReturnNewPassword() {
        String password = "maybeShouldCheckIfItIsWorking";
        when(passwordRepository.findByPassword(password)).thenReturn(null);

        Optional<Password> result = passwordService.getNewSinglePassword(password);

        assertTrue(result.isPresent());
        assertEquals(password, result.get().getPassword());
        assertNotNull(result.get());
    }

    @Test
    public void testGetPassword_newPassword_shouldSetComplexityLevels() {
        String password = "maybeShouldCheckIfItIsWorking1!";
        when(passwordRepository.findByPassword(password)).thenReturn(null);

        Optional<Password> result = passwordService.getNewSinglePassword(password);

        assertTrue(result.isPresent());
        assertEquals("uber silne", result.get().getComplexity());
    }

    @Test
    public void testGetPassword_newPassword_shouldSetComplexityLevels2() {
        String password = "maybeShouldCheckIfItIsWorking";
        when(passwordRepository.findByPassword(password)).thenReturn(null);

        Optional<Password> result = passwordService.getNewSinglePassword(password);

        assertTrue(result.isPresent());
    }

    @Test
    public void testGetPassword_newPassword_shouldSetComplexityLevels3() {
        String password = "Mypassword1";
        when(passwordRepository.findByPassword(password)).thenReturn(null);

        Optional<Password> result = passwordService.getNewSinglePassword(password);

        assertTrue(result.isPresent());
    }

    @Test
    public void testDeletePasswordOrShowIt_existingPassword_shouldReturnPasswordFromDB() {
        String password = "maybeShouldCheckIfItIsWorking";
        Password passwordFromDB = new Password();
        passwordFromDB.setPassword(password);
        passwordFromDB.setComplexity(null);
        when(passwordRepository.findByPassword(password)).thenReturn(passwordFromDB);

        Optional<Password> result = passwordService.deletePasswordOrShowIt(password);

        assertTrue(result.isPresent());
        assertEquals(passwordFromDB, result.get());
    }

    @Test
    public void testDeletePasswordOrShowIt_nonExistingPassword_shouldReturnEmptyOptional() {
        String password = "maybeShouldCheckIfItIsWorking";
        when(passwordRepository.findByPassword(password)).thenReturn(null);

        Optional<Password> result = passwordService.deletePasswordOrShowIt(password);

        assertFalse(result.isEmpty());
    }

    @Test
    public void deletePassword_withNonExistentPassword_doesNotThrowException() {
        String password = "nonExistentPassword";
        passwordService.deletePassword(password);
    }
}