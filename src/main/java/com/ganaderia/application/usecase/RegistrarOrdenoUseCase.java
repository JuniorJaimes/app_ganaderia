package com.ganaderia.application.usecase;

import com.ganaderia.application.dto.RegistrarOrdenoDTO;
import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.domain.event.ProduccionRegistradaEvent;
import com.ganaderia.domain.exception.DominioException;
import com.ganaderia.domain.model.*;
import com.ganaderia.domain.repository.RegistroOrdenoRepository;
import com.ganaderia.domain.repository.VacaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Caso de uso: Registrar un ordeño (producción de leche).
 * Valida existencia de la vaca, delega chequeo de estado al dominio,
 * y asegura la invariante de máximo 2 ordeños/día (1 por turno).
 */
public class RegistrarOrdenoUseCase {

    private final VacaRepository vacaRepository;
    private final RegistroOrdenoRepository ordenoRepository;
    private final EventPublisher eventPublisher;

    public RegistrarOrdenoUseCase(VacaRepository vacaRepository,
                                   RegistroOrdenoRepository ordenoRepository,
                                   EventPublisher eventPublisher) {
        this.vacaRepository = vacaRepository;
        this.ordenoRepository = ordenoRepository;
        this.eventPublisher = eventPublisher;
    }

    public String ejecutar(RegistrarOrdenoDTO dto) {
        IdVaca idVaca = new IdVaca(dto.vacaId());
        Vaca vaca = vacaRepository.obtenerPorId(idVaca);

        TurnoOrdeno turno = TurnoOrdeno.valueOf(dto.turno().toUpperCase());
        VolumenLeche volumen = new VolumenLeche(dto.litros());

        // Invariante: no se permiten ordeños duplicados (misma vaca, fecha, turno)
        ordenoRepository.buscarPorVacaFechaYTurno(idVaca, dto.fecha(), turno)
                .ifPresent(existente -> {
                    throw new DominioException(
                            "Ya existe un ordeño registrado para la vaca " + vaca.getNumeroArete()
                                    + " en el turno " + turno + " del " + dto.fecha()
                    );
                });

        // La validación de estado (SECA vs EN_PRODUCCION) vive en el constructor de RegistroOrdeno
        String idRegistro = UUID.randomUUID().toString();
        RegistroOrdeno registro = new RegistroOrdeno(idRegistro, vaca, dto.fecha(), turno, volumen);

        ordenoRepository.guardar(registro);

        eventPublisher.publicar(List.of(
                new ProduccionRegistradaEvent(idVaca.value(), dto.fecha(), turno.name(), volumen.litros())
        ));

        return idRegistro;
    }
}
