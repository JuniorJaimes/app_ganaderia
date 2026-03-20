package com.ganaderia.application.usecase;

import com.ganaderia.application.port.in.RegistrarInseminacionUseCase;
import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.domain.model.IdVaca;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.repository.VacaRepository;

import java.time.LocalDate;

public class RegistrarInseminacionUseCaseImpl implements RegistrarInseminacionUseCase {

    private final VacaRepository vacaRepository;
    private final EventPublisher eventPublisher;

    public RegistrarInseminacionUseCaseImpl(VacaRepository vacaRepository, EventPublisher eventPublisher) {
        this.vacaRepository = vacaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void ejecutar(String vacaId, LocalDate fecha, String toroId, String observaciones) {
        IdVaca id = new IdVaca(vacaId);
        Vaca vaca = vacaRepository.obtenerPorId(id);

        vaca.registrarInseminacion(fecha, toroId, observaciones);

        vacaRepository.guardar(vaca);
        eventPublisher.publicar(vaca.consumirEventos());
    }
}
