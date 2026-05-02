package com.ganaderia.application.usecase;

import com.ganaderia.application.dto.RegistrarPartoDTO;
import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.repository.VacaRepository;

/**
 * Caso de uso: Registrar un parto.
 * Efecto: la vaca pasa a estado EN_PRODUCCION, se genera un idTernero.
 */
public class RegistrarPartoUseCase {

    private final VacaRepository vacaRepository;
    private final EventPublisher eventPublisher;

    public RegistrarPartoUseCase(VacaRepository vacaRepository, EventPublisher eventPublisher) {
        this.vacaRepository = vacaRepository;
        this.eventPublisher = eventPublisher;
    }

    public String ejecutar(RegistrarPartoDTO dto) {
        IdVaca idMadre = new IdVaca(dto.vacaId());
        Vaca madre = vacaRepository.obtenerPorId(idMadre);

        // La lógica de negocio (invariantes biológicas) vive en Vaca, no aquí.
        String idTernero = madre.registrarParto(dto.fechaParto());

        vacaRepository.guardar(madre);
        eventPublisher.publicar(madre.consumirEventos());

        return idTernero;
    }
}
