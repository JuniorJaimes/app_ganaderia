package com.ganaderia.domain.model;

/**
 * Value Object que representa el identificador único de una vaca.
 * Inmutable. Valida que el valor no sea nulo ni vacío.
 */
public record IdVaca(String value) {

    public IdVaca {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El ID de la vaca no puede estar vacío");
        }
    }
}
