package com.ganaderia.application.usecase;

import com.ganaderia.application.dto.RegistrarVacaDTO;
import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.repository.VacaRepository;

import java.util.UUID;

/**
 * Caso de uso: Registrar una nueva vaca en el sistema.
 * Orquesta la creación del agregado y la persistencia.
 * No contiene lógica de negocio — solo dirige el flujo.
 */
public class RegistrarVacaUseCase {

    private final VacaRepository vacaRepository;
    private final EventPublisher eventPublisher;

    public RegistrarVacaUseCase(VacaRepository vacaRepository, EventPublisher eventPublisher) {
        this.vacaRepository = vacaRepository;
        this.eventPublisher = eventPublisher;
    }

    public String ejecutar(RegistrarVacaDTO dto) {
        IdVaca id = new IdVaca(UUID.randomUUID().toString());
        Vaca vaca = new Vaca(id, dto.numeroArete(), dto.fechaNacimiento());

        vacaRepository.guardar(vaca);
        eventPublisher.publicar(vaca.consumirEventos());

        return id.value();
    }
}
