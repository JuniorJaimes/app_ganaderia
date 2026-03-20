package com.ganaderia.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Evento emitido cuando una vaca es secada (fin de ciclo de lactancia).
 * Inmutable.
 */
public record VacaSecadaEvent(
        String vacaId,
        LocalDate fechaSecado,
        LocalDateTime timestamp
) {
    public VacaSecadaEvent(String vacaId, LocalDate fechaSecado) {
        this(vacaId, fechaSecado, LocalDateTime.now());
    }
}
