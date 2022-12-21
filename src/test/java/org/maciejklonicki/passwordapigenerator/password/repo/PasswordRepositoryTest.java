package org.maciejklonicki.passwordapigenerator.password.repo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.maciejklonicki.passwordapigenerator.password.Password;
import org.maciejklonicki.passwordapigenerator.password.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
class PasswordRepositoryTest {

    @Autowired
    private PasswordRepository passwordRepository;

    @Test
    void checkIfCanFindByPassword() {
        //given
        Password password = new Password();
        password.setPassword("test");
        password.setComplexity("słabe");
        password.setDateOfPasswordCreation();
        passwordRepository.save(password);
        //when
        Password checkPassword = passwordRepository.findByPassword("test");
        //then
        assertThat(checkPassword).isNotNull();
        assertThat(checkPassword.getPassword()).isEqualTo("test");
    }

    @Test
    void checkIfCanDeleteByPassword() {
        //given
        Password password = new Password();
        password.setPassword("test");
        password.setComplexity("");
        password.setDateOfPasswordCreation();
        //when
        passwordRepository.deleteByPassword("test");
        //then
        Password checkPassword = passwordRepository.findByPassword("test");
        assertThat(checkPassword).isNull();
    }
}