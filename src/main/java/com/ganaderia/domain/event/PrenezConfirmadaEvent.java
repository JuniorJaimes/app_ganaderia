package com.ganaderia.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Evento emitido cuando se confirma la preñez de una vaca.
 * Inmutable.
 */
public record PrenezConfirmadaEvent(
        String vacaId,
        LocalDate fechaConfirmacion,
        LocalDateTime timestamp
) {
    public PrenezConfirmadaEvent(String vacaId, LocalDate fechaConfirmacion) {
        this(vacaId, fechaConfirmacion, LocalDateTime.now());
    }
}
