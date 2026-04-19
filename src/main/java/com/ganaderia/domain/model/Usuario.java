package com.ganaderia.domain.model;

import com.ganaderia.domain.model.enums.Rol;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Usuario {
    private final Long id;
    private final String email;
    private String passwordHasheada;
    private Rol rol;
    private boolean activo;

    public Usuario(Long id, String email, String passwordHasheada, Rol rol, boolean activo) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (passwordHasheada == null || passwordHasheada.isBlank()) {
            throw new IllegalArgumentException("El password es obligatorio");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
        this.id = id;
        this.email = email;
        this.passwordHasheada = passwordHasheada;
        this.rol = rol;
        this.activo = activo;
    }

    public void desactivar() {
        this.activo = false;
    }

    public void activar() {
        this.activo = true;
    }
}
