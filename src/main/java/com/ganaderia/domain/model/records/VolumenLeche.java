package com.ganaderia.domain.model.records;

/**
 * Value Object que representa el volumen de leche en litros de un ordeño.
 * Inmutable. Valida que el volumen sea positivo y biológicamente razonable.
 */
public record VolumenLeche(double litros) {

    private static final double VOLUMEN_MAXIMO_RAZONABLE = 60.0;

    public VolumenLeche {
        if (litros <= 0.0) {
            throw new IllegalArgumentException("El volumen debe ser mayor a cero");
        }
        if (litros > VOLUMEN_MAXIMO_RAZONABLE) {
            throw new IllegalArgumentException(
                    "Volumen de ordeño anómalo detectado: " + litros + "L excede el límite de " + VOLUMEN_MAXIMO_RAZONABLE + "L"
            );
        }
    }
}
