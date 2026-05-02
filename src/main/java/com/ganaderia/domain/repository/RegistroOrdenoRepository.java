package com.ganaderia.domain.repository;

import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.model.RegistroOrdeno;
import com.ganaderia.domain.model.enums.TurnoOrdeno;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (interfaz) del dominio para persistir registros de ordeño.
 * La implementación concreta vive en infraestructura.
 */
public interface RegistroOrdenoRepository {

    void guardar(RegistroOrdeno registro);

    List<RegistroOrdeno> listarPorVaca(IdVaca vacaId);

    Optional<RegistroOrdeno> buscarPorVacaFechaYTurno(IdVaca vacaId, LocalDate fecha, TurnoOrdeno turno);
}
