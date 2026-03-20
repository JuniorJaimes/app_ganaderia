package com.ganaderia.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Evento emitido cuando una vaca registra un parto.
 * Inicia automáticamente un nuevo ciclo de lactancia.
 * Inmutable.
 */
public record PartoRegistradoEvent(
        String vacaId,
        LocalDate fechaParto,
        String idTernero,
        LocalDateTime timestamp
) {
    public PartoRegistradoEvent(String vacaId, LocalDate fechaParto, String idTernero) {
        this(vacaId, fechaParto, idTernero, LocalDateTime.now());
    }
}
