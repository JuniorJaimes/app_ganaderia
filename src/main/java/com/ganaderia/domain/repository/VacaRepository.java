package com.ganaderia.domain.repository;

import com.ganaderia.domain.model.IdVaca;
import com.ganaderia.domain.model.Vaca;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (interfaz) del dominio para persistir vacas.
 * La implementación concreta vive en infraestructura.
 * El dominio NUNCA conoce los detalles de la persistencia.
 */
public interface VacaRepository {

    Vaca obtenerPorId(IdVaca id);

    Optional<Vaca> buscarPorArete(String numeroArete);

    void guardar(Vaca vaca);

    List<Vaca> listarTodas();
}
