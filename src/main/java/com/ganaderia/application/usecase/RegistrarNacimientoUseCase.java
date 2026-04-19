package com.ganaderia.application.usecase;

import com.ganaderia.application.port.in.RegistrarNacimientoCommand;
import com.ganaderia.domain.exception.DominioException;
import com.ganaderia.domain.model.IdTernero;
import com.ganaderia.domain.model.IdVaca;
import com.ganaderia.domain.model.enums.Sexo;
import com.ganaderia.domain.model.Ternero;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.repository.TerneroRepository;
import com.ganaderia.domain.repository.VacaRepository;
import com.ganaderia.application.port.out.EventPublisher;

public class RegistrarNacimientoUseCase {

    private final VacaRepository vacaRepository;
    private final TerneroRepository terneroRepository;
    private final EventPublisher eventPublisher;

    public RegistrarNacimientoUseCase(VacaRepository vacaRepository, TerneroRepository terneroRepository, EventPublisher eventPublisher) {
        this.vacaRepository = vacaRepository;
        this.terneroRepository = terneroRepository;
        this.eventPublisher = eventPublisher;
    }

    public String ejecutar(RegistrarNacimientoCommand command) {
        IdVaca idMadre = new IdVaca(command.idMadre());

        Vaca madre = vacaRepository.obtenerPorId(idMadre);
        if (madre == null) {
            throw new DominioException("Madre no encontrada con ID: " + command.idMadre());
        }

        // Invariantes en el agregado Vaca: registra el parto, genera idTernero e hitos.
        String newIdTerneroStr = madre.registrarParto(command.fechaNacimiento());
        IdTernero idTernero = new IdTernero(newIdTerneroStr);

        Sexo sexo;
        try {
            sexo = Sexo.valueOf(command.sexo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DominioException("Sexo no válido. Valores omitidos: MACHO, HEMBRA");
        }

        // Crea el Ternero según el nacimiento
        Ternero nuevoTernero = Ternero.nacerDe(
                idTernero,
                idMadre,
                command.fechaNacimiento(),
                sexo
        );

        // Orquesta la transacción de dominio: Se guardan Vaca (actualizada) y Ternero (nuevo) juntos.
        // Ambos repositorios deberían ser transaccionalmente consistentes (ej. usando @Transactional en el punto de entrada).
        vacaRepository.guardar(madre);
        terneroRepository.guardar(nuevoTernero);

        // Publicar eventos de dominio después de guardar exitosamente
        eventPublisher.publicar(madre.consumirEventos());
        eventPublisher.publicar(nuevoTernero.consumirEventos());

        return idTernero.value();
    }
}
