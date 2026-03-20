package com.ganaderia.domain.event;

import java.time.LocalDate;

public record TerneroAdquiridoEvent(
        String idTernero,
        LocalDate fechaNacimiento,
        String sexo
) {}
