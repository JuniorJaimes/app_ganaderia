package com.ganaderia.infrastructure.adapter.out.persistence;

import com.ganaderia.application.port.out.UsuarioRepositoryPort;
import com.ganaderia.domain.model.Usuario;
import com.ganaderia.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaUsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final SpringDataUsuarioRepository repository;

    public JpaUsuarioRepositoryAdapter(SpringDataUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = toEntity(usuario);
        UsuarioEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    private Usuario toDomain(UsuarioEntity entity) {
        return Usuario.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .passwordHasheada(entity.getPasswordHasheada())
                .rol(entity.getRol())
                .activo(entity.isActivo())
                .build();
    }

    private UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setEmail(usuario.getEmail());
        entity.setPasswordHasheada(usuario.getPasswordHasheada());
        entity.setRol(usuario.getRol());
        entity.setActivo(usuario.isActivo());
        return entity;
    }
}
