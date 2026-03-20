package com.ganaderia.domain.repository;

import com.ganaderia.domain.model.IdTernero;
import com.ganaderia.domain.model.Ternero;

import java.util.Optional;

public interface TerneroRepository {
    void guardar(Ternero ternero);
    Optional<Ternero> buscarPorId(IdTernero id);
}
