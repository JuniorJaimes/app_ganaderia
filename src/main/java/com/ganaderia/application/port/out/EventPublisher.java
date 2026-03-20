package com.ganaderia.application.port.out;

import java.util.List;

/**
 * Puerto de salida: publicador de eventos de dominio.
 * La infraestructura decide cómo despachar los eventos
 * (bus en memoria, log, notificación, etc).
 */
public interface EventPublisher {
    void publicar(List<Object> eventos);
}
