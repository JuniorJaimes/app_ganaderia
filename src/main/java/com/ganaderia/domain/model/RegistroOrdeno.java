package com.ganaderia.domain.model;

import com.ganaderia.domain.exception.DominioException;
import com.ganaderia.domain.model.enums.TurnoOrdeno;

import java.time.LocalDate;

/**
 * Aggregate Root: Registro de Ordeño.
 * Representa un único evento de producción de leche (un turno, una fecha, una vaca).
 * Inmutable una vez creado (historial inmutable).
 * Valida contra el estado productivo de la vaca.
 */
public class RegistroOrdeno {

    private final String id;
    private final IdVaca vacaId;
    private final LocalDate fecha;
    private final TurnoOrdeno turno;
    private final VolumenLeche volumen;


    // Constructor: crea un registro validando contra la Vaca


    public RegistroOrdeno(String id, Vaca vaca, LocalDate fecha, TurnoOrdeno turno, VolumenLeche volumen) {
        if (id == null || id.isBlank()) {
            throw new DominioException("El ID del registro de ordeño no puede estar vacío");
        }
        if (vaca == null) {
            throw new DominioException("La vaca es obligatoria para registrar un ordeño");
        }
        if (!vaca.puedeSerOrdenada()) {
            throw new DominioException(
                    "No se puede registrar ordeño: La vaca " + vaca.getNumeroArete() + " está SECA"
            );
        }
        if (fecha == null) {
            throw new DominioException("La fecha del ordeño es obligatoria");
        }
        if (turno == null) {
            throw new DominioException("El turno del ordeño es obligatorio");
        }
        if (volumen == null) {
            throw new DominioException("El volumen de leche es obligatorio");
        }

        this.id = id;
        this.vacaId = vaca.getId();
        this.fecha = fecha;
        this.turno = turno;
        this.volumen = volumen;
    }


    // Constructor de reconstitución (desde BD)


    public static RegistroOrdeno reconstituir(String id, IdVaca vacaId, LocalDate fecha,
                                               TurnoOrdeno turno, VolumenLeche volumen) {
        return new RegistroOrdeno(id, vacaId, fecha, turno, volumen);
    }

    private RegistroOrdeno(String id, IdVaca vacaId, LocalDate fecha,
                           TurnoOrdeno turno, VolumenLeche volumen) {
        this.id = id;
        this.vacaId = vacaId;
        this.fecha = fecha;
        this.turno = turno;
        this.volumen = volumen;
    }


    public String getId() { return id; }
    public IdVaca getVacaId() { return vacaId; }
    public LocalDate getFecha() { return fecha; }
    public TurnoOrdeno getTurno() { return turno; }
    public VolumenLeche getVolumen() { return volumen; }
}
