package com.ganaderia.infrastructure.adapter.out.persistence.mapper;

import com.ganaderia.domain.model.*;
import com.ganaderia.domain.model.enums.TurnoOrdeno;
import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.model.records.VolumenLeche;
import com.ganaderia.infrastructure.adapter.out.persistence.entity.RegistroOrdenoEntity;

/**
 * Mapper bidireccional: RegistroOrdeno (dominio) <-> RegistroOrdenoEntity (JPA).
 */
public class RegistroOrdenoMapper {

    public static RegistroOrdenoEntity toEntity(RegistroOrdeno registro) {
        return new RegistroOrdenoEntity(
                registro.getId(),
                registro.getVacaId().value(),
                registro.getFecha(),
                registro.getTurno().name(),
                registro.getVolumen().litros()
        );
    }

    public static RegistroOrdeno toDomain(RegistroOrdenoEntity entity) {
        return RegistroOrdeno.reconstituir(
                entity.getId(),
                new IdVaca(entity.getVacaId()),
                entity.getFecha(),
                TurnoOrdeno.valueOf(entity.getTurno()),
                new VolumenLeche(entity.getLitros())
        );
    }
}
