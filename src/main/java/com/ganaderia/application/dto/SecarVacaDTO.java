package com.ganaderia.application.dto;

import java.time.LocalDate;

public record SecarVacaDTO(
        String vacaId,
        LocalDate fechaSecado
) {}
