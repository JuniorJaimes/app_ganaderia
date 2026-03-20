package com.ganaderia.application.dto;

import java.time.LocalDate;

public record RegistrarOrdenoDTO(
        String vacaId,
        LocalDate fecha,
        String turno,
        double litros
) {}
