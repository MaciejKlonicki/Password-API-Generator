package org.maciejklonicki.passwordapigenerator.password;

import lombok.RequiredArgsConstructor;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordRepository passwordRepository;

    public List<Password> getAllPasswords() {
        return passwordRepository.findAll();
    }


    public Password createPassword(Password password) {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        List<CharacterRule> rules = Arrays.asList(
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );
        String newPassword = passwordGenerator.generatePassword(8, rules);
        password.setPassword(newPassword);
        password.setDateOfPasswordCreation();

        return passwordRepository.save(password);
    }
}
