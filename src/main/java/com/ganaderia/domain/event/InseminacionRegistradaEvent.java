package com.ganaderia.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Evento emitido cuando se insemina una vaca.
 * Inmutable.
 */
public record InseminacionRegistradaEvent(
        String vacaId,
        LocalDate fechaInseminacion,
        String toroId,
        LocalDateTime timestamp
) {
    public InseminacionRegistradaEvent(String vacaId, LocalDate fechaInseminacion, String toroId) {
        this(vacaId, fechaInseminacion, toroId, LocalDateTime.now());
    }
}
