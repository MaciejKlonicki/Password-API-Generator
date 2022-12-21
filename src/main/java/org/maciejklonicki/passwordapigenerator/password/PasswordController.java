package org.maciejklonicki.passwordapigenerator.password;

import lombok.RequiredArgsConstructor;
import org.maciejklonicki.passwordapigenerator.password.dto.NewPasswordSingleDto;
import org.maciejklonicki.passwordapigenerator.password.dto.PasswordListDto;
import org.maciejklonicki.passwordapigenerator.password.dto.PasswordSingleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.maciejklonicki.passwordapigenerator.password.mapper.NewPasswordSingleDtoMapper.mapToNewPasswordSingleDto;
import static org.maciejklonicki.passwordapigenerator.password.mapper.PasswordListDtoMapper.mapToPasswordListDto;
import static org.maciejklonicki.passwordapigenerator.password.mapper.PasswordSingleDtoMapper.mapToPasswordSingleDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/passwords")
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping
    public List<PasswordListDto> getAllPasswords() {
        return mapToPasswordListDto(passwordService.getAllPasswords());
    }

    @GetMapping("{password}")
    public List<PasswordSingleDto> getSinglePassword(@PathVariable String password) {
        return mapToPasswordSingleDto(passwordService.getSinglePassword(password));
    }

    @GetMapping("/new/{password}")
    public List<NewPasswordSingleDto> getNewSinglePassword(@PathVariable String password) {
        return mapToNewPasswordSingleDto(passwordService.getNewSinglePassword(password));
    }

    @PostMapping
    public ResponseEntity<Password> createPassword (@RequestBody Password password) {
        return passwordService.createPassword(password);
    }

}
