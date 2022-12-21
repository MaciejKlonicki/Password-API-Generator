package org.maciejklonicki.passwordapigenerator.password.mapper;

import org.maciejklonicki.passwordapigenerator.password.Password;
import org.maciejklonicki.passwordapigenerator.password.dto.PasswordSingleDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PasswordSingleDtoMapper {
    public static List<PasswordSingleDto> mapToPasswordSingleDto(Optional<Password> singlePassword) {
        return singlePassword.stream()
                .map(password1 -> new PasswordSingleDto(password1.getId(), password1.getDateOfPasswordCreation(), password1.getComplexity()))
                .collect(Collectors.toList());
    }
}
