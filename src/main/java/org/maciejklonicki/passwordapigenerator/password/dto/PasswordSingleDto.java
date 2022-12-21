package org.maciejklonicki.passwordapigenerator.password.dto;

import java.time.Instant;

public record PasswordSingleDto (Long id, Instant dateOfPasswordCreation, String complexity) {
}
