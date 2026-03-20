package com.ganaderia.infrastructure.adapter.out.persistence;

import com.ganaderia.infrastructure.adapter.out.persistence.entity.VacaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interfaz interna de Spring Data JPA.
 * Solo la usa el adaptador JpaVacaRepositoryAdapter. No la conoce el dominio.
 */
public interface SpringDataVacaRepository extends JpaRepository<VacaEntity, String> {

    Optional<VacaEntity> findByNumeroArete(String numeroArete);
}
