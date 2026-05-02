package com.ganaderia.infrastructure.adapter.out.persistence;

import com.ganaderia.domain.exception.DominioException;
import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.repository.VacaRepository;
import com.ganaderia.infrastructure.adapter.out.persistence.entity.VacaEntity;
import com.ganaderia.infrastructure.adapter.out.persistence.mapper.VacaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia: implementa el contrato del dominio usando Spring Data JPA.
 * Esta clase es el "puente" entre lo que el dominio necesita y lo que la BD ofrece.
 */
@Repository
public class JpaVacaRepositoryAdapter implements VacaRepository {

    private final SpringDataVacaRepository springDataRepo;

    public JpaVacaRepositoryAdapter(SpringDataVacaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Vaca obtenerPorId(IdVaca id) {
        VacaEntity entity = springDataRepo.findById(id.value())
                .orElseThrow(() -> new DominioException("No se encontró una vaca con ID: " + id.value()));
        return VacaMapper.toDomain(entity);
    }

    @Override
    public Optional<Vaca> buscarPorArete(String numeroArete) {
        return springDataRepo.findByNumeroArete(numeroArete)
                .map(VacaMapper::toDomain);
    }

    @Override
    public void guardar(Vaca vaca) {
        VacaEntity entity = VacaMapper.toEntity(vaca);
        springDataRepo.save(entity);
    }

    @Override
    public List<Vaca> listarTodas() {
        return springDataRepo.findAll().stream()
                .map(VacaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vaca> listarVacasPorEstadoReproductivo(com.ganaderia.domain.model.enums.EstadoReproductivo estado) {
        return springDataRepo.findByEstadoReproductivo(estado.name()).stream()
                .map(VacaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
