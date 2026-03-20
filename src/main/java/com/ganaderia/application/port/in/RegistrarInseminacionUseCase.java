package com.ganaderia.application.port.in;

import java.time.LocalDate;

public interface RegistrarInseminacionUseCase {
    void ejecutar(String vacaId, LocalDate fecha, String toroId, String observaciones);
}
