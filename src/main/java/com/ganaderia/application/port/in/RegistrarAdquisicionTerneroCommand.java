package com.ganaderia.application.port.in;

import java.time.LocalDate;

public record RegistrarAdquisicionTerneroCommand(
        LocalDate fechaNacimiento,
        String sexo
) {
    public RegistrarAdquisicionTerneroCommand {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fechaNacimiento es obligatoria");
        }
        if (sexo == null || sexo.isBlank()) {
            throw new IllegalArgumentException("El sexo es obligatorio");
        }
    }
}
