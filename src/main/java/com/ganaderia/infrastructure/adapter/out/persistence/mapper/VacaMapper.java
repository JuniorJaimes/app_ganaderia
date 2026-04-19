package com.ganaderia.infrastructure.adapter.out.persistence.mapper;

import com.ganaderia.domain.model.*;
import com.ganaderia.domain.model.enums.EstadoProductivo;
import com.ganaderia.infrastructure.adapter.out.persistence.entity.VacaEntity;

/**
 * Mapper bidireccional: Vaca (dominio) <-> VacaEntity (JPA).
 * Evita que el dominio se contamine con anotaciones JPA.
 */
public class VacaMapper {

    public static VacaEntity toEntity(Vaca vaca) {
        return new VacaEntity(
                vaca.getId().value(),
                vaca.getNumeroArete(),
                vaca.getFechaNacimiento(),
                vaca.getEstadoActual().name(),
                vaca.getFechaUltimoParto()
        );
    }

    public static Vaca toDomain(VacaEntity entity) {
        return Vaca.reconstituir(
                new IdVaca(entity.getId()),
                entity.getNumeroArete(),
                entity.getFechaNacimiento(),
                EstadoProductivo.valueOf(entity.getEstadoActual()),
                entity.getFechaUltimoParto()
        );
    }
}
