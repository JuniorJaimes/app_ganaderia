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
}
