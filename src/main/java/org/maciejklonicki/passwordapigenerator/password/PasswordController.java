package org.maciejklonicki.passwordapigenerator.password;

import lombok.RequiredArgsConstructor;
import org.maciejklonicki.passwordapigenerator.password.dto.PasswordListDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.maciejklonicki.passwordapigenerator.password.mapper.PasswordListDtoMapper.mapToPasswordListDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/passwords")
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping
    public List<PasswordListDto> getAllPasswords() {
        return mapToPasswordListDto(passwordService.getAllPasswords());
    }

    @PostMapping
    public Password createPassword (@RequestBody Password password) {
        return passwordService.createPassword(password);
    }

}
