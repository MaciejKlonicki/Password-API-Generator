package org.maciejklonicki.passwordapigenerator.password.mapper;

import org.maciejklonicki.passwordapigenerator.password.Password;
import org.maciejklonicki.passwordapigenerator.password.dto.PasswordListDto;

import java.util.List;
import java.util.stream.Collectors;

public class PasswordListDtoMapper {

    public static List<PasswordListDto> mapToPasswordListDto(List<Password> passwords) {
        return passwords.stream()
                .map(password -> new PasswordListDto(password.getId(), password.getPassword(), password.getComplexity()))
                .collect(Collectors.toList());
    }
}
