package com.ganaderia.domain.model;

import com.ganaderia.domain.exception.DominioException;

import java.time.LocalDate;

/**
 * Value Object: Representa un evento reproductivo en el historial de una vaca.
 * Inmutable una vez creado. Forma parte del historial reproductivo de la Vaca.
 */
public class EventoReproductivo {

    public enum TipoEventoReproductivo {
        CELO_DETECTADO,
        INSEMINACION,
        PALPACION_POSITIVA,
        PALPACION_NEGATIVA,
        PALPACION_DUDOSA
    }

    private final TipoEventoReproductivo tipo;
    private final LocalDate fecha;
    private final String observaciones;
    private final String toroId; // Solo aplica para INSEMINACION

    public EventoReproductivo(TipoEventoReproductivo tipo, LocalDate fecha, String observaciones, String toroId) {
        if (tipo == null) {
            throw new DominioException("El tipo de evento reproductivo es obligatorio");
        }
        if (fecha == null) {
            throw new DominioException("La fecha del evento reproductivo es obligatoria");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new DominioException("La fecha del evento reproductivo no puede ser futura");
        }
        if (tipo == TipoEventoReproductivo.INSEMINACION && (toroId == null || toroId.isBlank())) {
            throw new DominioException("El ID del toro es obligatorio para una inseminación");
        }

        this.tipo = tipo;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.toroId = toroId;
    }

    public TipoEventoReproductivo getTipo() { return tipo; }
    public LocalDate getFecha() { return fecha; }
    public String getObservaciones() { return observaciones; }
    public String getToroId() { return toroId; }
}
