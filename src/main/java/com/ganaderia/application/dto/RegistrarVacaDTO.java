package com.ganaderia.application.dto;

import java.time.LocalDate;

public record RegistrarVacaDTO(
        String numeroArete,
        LocalDate fechaNacimiento
) {}
