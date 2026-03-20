package com.ganaderia.application.port.in;

import java.time.LocalDate;

public record RegistrarNacimientoCommand(
        String idMadre,
        LocalDate fechaNacimiento,
        String sexo
) {
    public RegistrarNacimientoCommand {
        if (idMadre == null || idMadre.isBlank()) {
            throw new IllegalArgumentException("El idMadre es obligatorio para registrar un nacimiento");
        }
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fechaNacimiento es obligatoria");
        }
        if (sexo == null || sexo.isBlank()) {
            throw new IllegalArgumentException("El sexo es obligatorio");
        }
    }
}
