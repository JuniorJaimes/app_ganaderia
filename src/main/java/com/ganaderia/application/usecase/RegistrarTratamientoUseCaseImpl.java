package com.ganaderia.application.usecase;

import com.ganaderia.application.port.in.RegistrarTratamientoUseCase;
import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.repository.VacaRepository;

import java.time.LocalDate;

public class RegistrarTratamientoUseCaseImpl implements RegistrarTratamientoUseCase {

    private final VacaRepository vacaRepository;
    private final EventPublisher eventPublisher;

    public RegistrarTratamientoUseCaseImpl(VacaRepository vacaRepository, EventPublisher eventPublisher) {
        this.vacaRepository = vacaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void ejecutar(String vacaId, String medicamento, String diagnostico,
                         LocalDate fechaAplicacion, LocalDate fechaFinRetiro, String observaciones) {
        IdVaca id = new IdVaca(vacaId);
        Vaca vaca = vacaRepository.obtenerPorId(id);

        vaca.registrarTratamiento(medicamento, diagnostico, fechaAplicacion, fechaFinRetiro, observaciones);

        vacaRepository.guardar(vaca);
        eventPublisher.publicar(vaca.consumirEventos());
    }
}
