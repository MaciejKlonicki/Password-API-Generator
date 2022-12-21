package org.maciejklonicki.passwordapigenerator.password.mapper;

import org.maciejklonicki.passwordapigenerator.password.Password;
import org.maciejklonicki.passwordapigenerator.password.dto.NewPasswordSingleDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NewPasswordSingleDtoMapper {

    public static List<NewPasswordSingleDto> mapToNewPasswordSingleDto(Optional<Password> newSinglePassword) {
        return newSinglePassword.stream()
                .map(password1 -> new NewPasswordSingleDto(password1.getPassword(), password1.getComplexity()))
                .collect(Collectors.toList());
    }
}
