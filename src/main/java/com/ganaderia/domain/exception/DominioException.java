package com.ganaderia.domain.exception;

/**
 * Excepción base del dominio.
 * Se lanza cuando una invariante del negocio es violada.
 * Es RuntimeException porque las violaciones del dominio son irrecuperables:
 * el flujo debe detenerse.
 */
public class DominioException extends RuntimeException {

    public DominioException(String mensaje) {
        super(mensaje);
    }
}
