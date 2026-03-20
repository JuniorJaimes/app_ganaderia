package com.ganaderia.infrastructure.adapter.out.persistence;

import com.ganaderia.domain.model.IdVaca;
import com.ganaderia.domain.model.RegistroOrdeno;
import com.ganaderia.domain.model.TurnoOrdeno;
import com.ganaderia.domain.repository.RegistroOrdenoRepository;
import com.ganaderia.infrastructure.adapter.out.persistence.entity.RegistroOrdenoEntity;
import com.ganaderia.infrastructure.adapter.out.persistence.mapper.RegistroOrdenoMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia para registros de ordeño.
 */
@Repository
public class JpaRegistroOrdenoRepositoryAdapter implements RegistroOrdenoRepository {

    private final SpringDataRegistroOrdenoRepository springDataRepo;

    public JpaRegistroOrdenoRepositoryAdapter(SpringDataRegistroOrdenoRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public void guardar(RegistroOrdeno registro) {
        RegistroOrdenoEntity entity = RegistroOrdenoMapper.toEntity(registro);
        springDataRepo.save(entity);
    }

    @Override
    public List<RegistroOrdeno> listarPorVaca(IdVaca vacaId) {
        return springDataRepo.findByVacaId(vacaId.value()).stream()
                .map(RegistroOrdenoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RegistroOrdeno> buscarPorVacaFechaYTurno(IdVaca vacaId, LocalDate fecha, TurnoOrdeno turno) {
        return springDataRepo.findByVacaIdAndFechaAndTurno(vacaId.value(), fecha, turno.name())
                .map(RegistroOrdenoMapper::toDomain);
    }
}
