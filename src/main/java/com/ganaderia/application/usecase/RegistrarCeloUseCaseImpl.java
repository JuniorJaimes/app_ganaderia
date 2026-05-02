package com.ganaderia.application.usecase;

import com.ganaderia.application.port.in.RegistrarCeloUseCase;
import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.repository.VacaRepository;

import java.time.LocalDate;

public class RegistrarCeloUseCaseImpl implements RegistrarCeloUseCase {

    private final VacaRepository vacaRepository;
    private final EventPublisher eventPublisher;

    public RegistrarCeloUseCaseImpl(VacaRepository vacaRepository, EventPublisher eventPublisher) {
        this.vacaRepository = vacaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void ejecutar(String vacaId, LocalDate fecha, String observaciones) {
        IdVaca id = new IdVaca(vacaId);
        Vaca vaca = vacaRepository.obtenerPorId(id);

        vaca.registrarCelo(fecha, observaciones);

        vacaRepository.guardar(vaca);
        eventPublisher.publicar(vaca.consumirEventos());
    }
}
