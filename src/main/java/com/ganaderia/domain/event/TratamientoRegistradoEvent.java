package com.ganaderia.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Evento emitido cuando se registra un tratamiento veterinario a una vaca.
 * Inmutable.
 */
public record TratamientoRegistradoEvent(
        String vacaId,
        String medicamento,
        String diagnostico,
        LocalDate fechaAplicacion,
        LocalDate fechaFinRetiro,
        LocalDateTime timestamp
) {
    public TratamientoRegistradoEvent(String vacaId, String medicamento, String diagnostico,
                                      LocalDate fechaAplicacion, LocalDate fechaFinRetiro) {
        this(vacaId, medicamento, diagnostico, fechaAplicacion, fechaFinRetiro, LocalDateTime.now());
    }
}
