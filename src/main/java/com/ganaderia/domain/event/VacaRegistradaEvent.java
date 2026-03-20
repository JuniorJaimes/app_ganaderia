package com.ganaderia.domain.event;

import java.time.LocalDateTime;

/**
 * Evento emitido cuando se registra una nueva vaca en el sistema.
 * Inmutable.
 */
public record VacaRegistradaEvent(
        String vacaId,
        String numeroArete,
        LocalDateTime timestamp
) {
    public VacaRegistradaEvent(String vacaId, String numeroArete) {
        this(vacaId, numeroArete, LocalDateTime.now());
    }
}
