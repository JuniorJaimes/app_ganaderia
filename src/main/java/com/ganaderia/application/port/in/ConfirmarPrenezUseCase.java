package com.ganaderia.application.port.in;

import java.time.LocalDate;

public interface ConfirmarPrenezUseCase {
    void ejecutar(String vacaId, LocalDate fecha, String observaciones);
}
