package com.ganaderia.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Evento emitido cuando se registra producción de leche en un ordeño.
 * Inmutable.
 */
public record ProduccionRegistradaEvent(
        String vacaId,
        LocalDate fecha,
        String turno,
        double litros,
        LocalDateTime timestamp
) {
    public ProduccionRegistradaEvent(String vacaId, LocalDate fecha, String turno, double litros) {
        this(vacaId, fecha, turno, litros, LocalDateTime.now());
    }
}
