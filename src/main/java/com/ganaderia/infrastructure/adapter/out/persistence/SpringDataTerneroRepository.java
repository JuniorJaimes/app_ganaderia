package com.ganaderia.infrastructure.adapter.out.persistence;

import com.ganaderia.infrastructure.adapter.out.persistence.entity.TerneroJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTerneroRepository extends JpaRepository<TerneroJpaEntity, String> {
}
