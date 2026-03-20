package com.ganaderia.domain.model;

import com.ganaderia.domain.exception.DominioException;

import java.time.LocalDate;

/**
 * Value Object: Representa un tratamiento veterinario aplicado a una vaca.
 * Inmutable una vez creado.
 *
 * El campo 'fechaFinRetiro' es crítico para la seguridad alimentaria:
 * mientras la fecha actual sea anterior a fechaFinRetiro, la leche
 * de esta vaca NO debe mezclarse con el tanque general.
 */
public class Tratamiento {

    private final String medicamento;
    private final String diagnostico;
    private final LocalDate fechaAplicacion;
    private final LocalDate fechaFinRetiro;
    private final String observaciones;

    public Tratamiento(String medicamento, String diagnostico, LocalDate fechaAplicacion,
                       LocalDate fechaFinRetiro, String observaciones) {
        if (medicamento == null || medicamento.isBlank()) {
            throw new DominioException("El medicamento es obligatorio");
        }
        if (diagnostico == null || diagnostico.isBlank()) {
            throw new DominioException("El diagnóstico es obligatorio");
        }
        if (fechaAplicacion == null) {
            throw new DominioException("La fecha de aplicación es obligatoria");
        }
        if (fechaAplicacion.isAfter(LocalDate.now())) {
            throw new DominioException("La fecha de aplicación no puede ser futura");
        }
        if (fechaFinRetiro != null && fechaFinRetiro.isBefore(fechaAplicacion)) {
            throw new DominioException("La fecha de fin de retiro no puede ser anterior a la de aplicación");
        }

        this.medicamento = medicamento;
        this.diagnostico = diagnostico;
        this.fechaAplicacion = fechaAplicacion;
        this.fechaFinRetiro = fechaFinRetiro;
        this.observaciones = observaciones;
    }

    /**
     * Determina si el período de retiro sigue activo.
     * Si no hay fecha de retiro, se asume que el medicamento no afecta la leche.
     */
    public boolean enPeriodoDeRetiro() {
        if (fechaFinRetiro == null) {
            return false;
        }
        return !LocalDate.now().isAfter(fechaFinRetiro);
    }

    public String getMedicamento() { return medicamento; }
    public String getDiagnostico() { return diagnostico; }
    public LocalDate getFechaAplicacion() { return fechaAplicacion; }
    public LocalDate getFechaFinRetiro() { return fechaFinRetiro; }
    public String getObservaciones() { return observaciones; }
}
