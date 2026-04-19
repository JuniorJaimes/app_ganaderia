package com.ganaderia.domain.model;

/**
 * Dimensión reproductiva de una vaca.
 * Independiente del estado productivo (ordeño).
 * Una vaca puede estar EN_PRODUCCION y CARGADA al mismo tiempo.
 */
public enum EstadoReproductivo {
    VACIA,
    INSEMINADA,
    CARGADA,
    DUDOSA
}
