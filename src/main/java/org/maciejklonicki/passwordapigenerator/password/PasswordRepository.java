package org.maciejklonicki.passwordapigenerator.password;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    Password findByPassword(String password);
}
