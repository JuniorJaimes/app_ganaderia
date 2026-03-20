package com.ganaderia.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Evento emitido cuando se detecta un celo en una vaca.
 * Inmutable.
 */
public record CeloDetectadoEvent(
        String vacaId,
        LocalDate fechaCelo,
        LocalDateTime timestamp
) {
    public CeloDetectadoEvent(String vacaId, LocalDate fechaCelo) {
        this(vacaId, fechaCelo, LocalDateTime.now());
    }
}
