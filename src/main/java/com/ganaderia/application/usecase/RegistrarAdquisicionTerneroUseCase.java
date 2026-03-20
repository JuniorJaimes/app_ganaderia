package com.ganaderia.application.usecase;

import com.ganaderia.application.port.in.RegistrarAdquisicionTerneroCommand;
import com.ganaderia.domain.exception.DominioException;
import com.ganaderia.domain.model.IdTernero;
import com.ganaderia.domain.model.Sexo;
import com.ganaderia.domain.model.Ternero;
import com.ganaderia.domain.repository.TerneroRepository;
import com.ganaderia.application.port.out.EventPublisher;

import java.util.UUID;

public class RegistrarAdquisicionTerneroUseCase {

    private final TerneroRepository terneroRepository;
    private final EventPublisher eventPublisher;

    public RegistrarAdquisicionTerneroUseCase(TerneroRepository terneroRepository, EventPublisher eventPublisher) {
        this.terneroRepository = terneroRepository;
        this.eventPublisher = eventPublisher;
    }

    public String ejecutar(RegistrarAdquisicionTerneroCommand command) {
        Sexo sexo;
        try {
            sexo = Sexo.valueOf(command.sexo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DominioException("Sexo no válido. Valores omitidos: MACHO, HEMBRA");
        }

        String newId = UUID.randomUUID().toString();
        IdTernero idTernero = new IdTernero(newId);

        Ternero nuevoTernero = Ternero.adquirir(
                idTernero,
                command.fechaNacimiento(),
                sexo
        );

        terneroRepository.guardar(nuevoTernero);

        eventPublisher.publicar(nuevoTernero.consumirEventos());

        return idTernero.value();
    }
}
