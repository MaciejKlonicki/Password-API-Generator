package org.maciejklonicki.passwordapigenerator.password;

import lombok.RequiredArgsConstructor;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordRepository passwordRepository;

    public List<Password> getAllPasswords() {
        return passwordRepository.findAll();
    }
    public ResponseEntity<Password> createPassword(Password password) {

        long passwordCount = passwordRepository.count();
        if (passwordCount >= 1000) {
            throw new IllegalArgumentException("Limit haseł został przekroczony!");
        }

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        List<CharacterRule> rules = Arrays.asList(
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );
        Random rand = new Random();
        int length = rand.nextInt(34) + 2;
        String newPassword = passwordGenerator.generatePassword(length, rules);
        if (newPassword.length() >= 3 && newPassword.length() <= 32) {
            password.setPassword(newPassword);
            password.setDateOfPasswordCreation();
            if (newPassword.length() <= 5) {
                password.setComplexity("słabe");
            } else if (newPassword.length() <= 8) {
                password.setComplexity("średnie");
            } else if (newPassword.length() <= 16) {
                password.setComplexity("silne");
            } else {
                password.setComplexity("uber silne");
            }
            Password savedPassword = passwordRepository.save(password);
            return new ResponseEntity<>(savedPassword, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
