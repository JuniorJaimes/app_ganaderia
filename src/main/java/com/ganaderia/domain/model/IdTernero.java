package com.ganaderia.domain.model;

public record IdTernero(String value) {
    public IdTernero {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El id del ternero no puede ser nulo o vacío");
        }
    }
}
