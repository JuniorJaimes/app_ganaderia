package com.ganaderia.infrastructure.adapter.out.persistence;

import com.ganaderia.domain.model.records.IdTernero;
import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.model.enums.Sexo;
import com.ganaderia.domain.model.Ternero;
import com.ganaderia.domain.model.enums.TipoIngresoTernero;
import com.ganaderia.domain.repository.TerneroRepository;
import com.ganaderia.infrastructure.adapter.out.persistence.entity.TerneroJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaTerneroRepositoryAdapter implements TerneroRepository {

    private final SpringDataTerneroRepository repository;

    public JpaTerneroRepositoryAdapter(SpringDataTerneroRepository repository) {
        this.repository = repository;
    }

    @Override
    public void guardar(Ternero ternero) {
        TerneroJpaEntity entity = ToJpaEntity(ternero);
        repository.save(entity);
    }

    @Override
    public Optional<Ternero> buscarPorId(IdTernero id) {
        return repository.findById(id.value())
                .map(this::ToDomainModel);
    }

    private TerneroJpaEntity ToJpaEntity(Ternero ternero) {
        return new TerneroJpaEntity(
                ternero.getId().value(),
                ternero.getIdMadre().map(IdVaca::value).orElse(null),
                ternero.getFechaNacimiento(),
                ternero.getSexo().name(),
                ternero.getTipoIngreso().name()
        );
    }

    private Ternero ToDomainModel(TerneroJpaEntity entity) {
        return Ternero.reconstituir(
                new IdTernero(entity.getId()),
                entity.getIdMadre() != null ? new IdVaca(entity.getIdMadre()) : null,
                entity.getFechaNacimiento(),
                Sexo.valueOf(entity.getSexo()),
                TipoIngresoTernero.valueOf(entity.getTipoIngreso())
        );
    }
}
