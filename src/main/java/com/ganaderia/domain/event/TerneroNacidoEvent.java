package com.ganaderia.domain.event;

import java.time.LocalDate;

public record TerneroNacidoEvent(
        String idTernero,
        String idMadre,
        LocalDate fechaNacimiento,
        String sexo
) {}
