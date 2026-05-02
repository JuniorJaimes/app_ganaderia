package com.ganaderia.application.usecase;

import com.ganaderia.application.dto.SecarVacaDTO;
import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.repository.VacaRepository;

/**
 * Caso de uso: Secar una vaca (fin de ciclo de lactancia).
 * La invariante (no secar una vaca ya seca) vive en el agregado Vaca.
 */
public class SecarVacaUseCase {

    private final VacaRepository vacaRepository;
    private final EventPublisher eventPublisher;

    public SecarVacaUseCase(VacaRepository vacaRepository, EventPublisher eventPublisher) {
        this.vacaRepository = vacaRepository;
        this.eventPublisher = eventPublisher;
    }

    public void ejecutar(SecarVacaDTO dto) {
        IdVaca id = new IdVaca(dto.vacaId());
        Vaca vaca = vacaRepository.obtenerPorId(id);

        vaca.secar(dto.fechaSecado());

        vacaRepository.guardar(vaca);
        eventPublisher.publicar(vaca.consumirEventos());
    }
}
