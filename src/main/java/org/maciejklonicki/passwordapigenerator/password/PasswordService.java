package org.maciejklonicki.passwordapigenerator.password;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<CharacterRule> rules;
            rules = Arrays.asList(
                    new CharacterRule(EnglishCharacterData.LowerCase, 1),
                    new CharacterRule(EnglishCharacterData.UpperCase, 1),
                    new CharacterRule(EnglishCharacterData.Special, 1)
            );

        Random rand = new Random();
        int length = rand.nextInt(30) + 3;
        String newPassword = passwordGenerator.generatePassword(length, rules);
        if (newPassword.length() > 3 && newPassword.length() <= 32) {
            password.setPassword(newPassword);
            password.setDateOfPasswordCreation();

            setComplexityLevels(password, newPassword);
            Password savedPassword = passwordRepository.save(password);
            return new ResponseEntity<>(savedPassword, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Optional<Password> getSinglePassword(String password) {
        return Optional.ofNullable(passwordRepository.findByPassword(password));
    }

    public Optional<Password> getNewSinglePassword(String password) {
        return getPassword(password);
    }
    @Transactional
    public Optional<Password> deletePasswordOrShowIt(String password) {
        return getPassword(password);
    }

    private Optional<Password> getPassword(String password) {
        Password passwordFromDB = passwordRepository.findByPassword(password);
        if (passwordFromDB != null) {
            return Optional.of(passwordFromDB);
        } else  {
            Password newPassword = new Password();
            newPassword.setPassword(password);
            setComplexityLevels(newPassword, password);
            return Optional.of(newPassword);
        }
    }

    @Transactional
    public void deletePassword(String password) {
        passwordRepository.deleteByPassword(password);
    }

    private static void setComplexityLevels(Password password, String newPassword) {
        boolean hasUppercase = !newPassword.equals(newPassword.toLowerCase());
        boolean hasLowercase = !newPassword.equals(newPassword.toUpperCase());
        boolean hasSpecial = newPassword.matches(".*[^A-Za-z0-9].*");

        if (newPassword.length() > 16 && hasSpecial && hasLowercase && hasUppercase){
            password.setComplexity("uber silne");
        } else if (newPassword.length() > 8 && newPassword.length() <= 16 && hasSpecial && hasLowercase && hasUppercase) {
            password.setComplexity("silne");
        } else if (newPassword.length() > 5 && newPassword.length() <= 8 && hasLowercase && hasUppercase){
            password.setComplexity("średnie");
        } else if (newPassword.length() <= 5 && (hasLowercase || hasUppercase)) {
            password.setComplexity("słabe");
        }
    }
}
