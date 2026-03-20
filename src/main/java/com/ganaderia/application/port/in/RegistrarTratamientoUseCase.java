package com.ganaderia.application.port.in;

import java.time.LocalDate;

public interface RegistrarTratamientoUseCase {
    void ejecutar(String vacaId, String medicamento, String diagnostico,
                  LocalDate fechaAplicacion, LocalDate fechaFinRetiro, String observaciones);
}
