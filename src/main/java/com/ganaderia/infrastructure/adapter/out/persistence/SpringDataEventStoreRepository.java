package com.ganaderia.infrastructure.adapter.out.persistence;

import com.ganaderia.infrastructure.adapter.out.persistence.entity.EventStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz interna de Spring Data JPA para la tabla de eventos (append-only).
 */
public interface SpringDataEventStoreRepository extends JpaRepository<EventStoreEntity, Long> {
}
