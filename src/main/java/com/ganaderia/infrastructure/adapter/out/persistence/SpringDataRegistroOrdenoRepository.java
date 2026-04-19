package com.ganaderia.infrastructure.adapter.out.persistence;

import com.ganaderia.infrastructure.adapter.out.persistence.entity.RegistroOrdenoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz interna de Spring Data JPA para registros de ordeño.
 */
public interface SpringDataRegistroOrdenoRepository extends JpaRepository<RegistroOrdenoEntity, String> {

    List<RegistroOrdenoEntity> findByVacaId(String vacaId);

    Optional<RegistroOrdenoEntity> findByVacaIdAndFechaAndTurno(String vacaId, LocalDate fecha, String turno);

    @org.springframework.data.jpa.repository.Query(
        "SELECT new com.ganaderia.application.dto.ReporteProduccionDTO(r.vacaId, v.numeroArete, SUM(r.litrosLeche)) " +
        "FROM RegistroOrdenoEntity r " +
        "JOIN VacaEntity v ON r.vacaId = v.id " +
        "WHERE YEAR(r.fecha) = :anio AND MONTH(r.fecha) = :mes " +
        "GROUP BY r.vacaId, v.numeroArete"
    )
    List<com.ganaderia.application.dto.ReporteProduccionDTO> sumarProduccionPorMesYAnio(
            @org.springframework.data.repository.query.Param("anio") int anio,
            @org.springframework.data.repository.query.Param("mes") int mes
    );
}
