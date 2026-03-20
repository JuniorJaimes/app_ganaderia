package com.ganaderia.application.dto;

import java.time.LocalDate;

public record RegistrarPartoDTO(
        String vacaId,
        LocalDate fechaParto
) {}
