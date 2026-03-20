package com.ganaderia.application.usecase;

import com.ganaderia.application.port.in.RegistrarUsuarioUseCase;
import com.ganaderia.application.port.out.PasswordEncoderPort;
import com.ganaderia.application.port.out.UsuarioRepositoryPort;
import com.ganaderia.domain.model.Rol;
import com.ganaderia.domain.model.Usuario;

public class RegistrarUsuarioUseCaseImpl implements RegistrarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;

    public RegistrarUsuarioUseCaseImpl(UsuarioRepositoryPort usuarioRepository, PasswordEncoderPort passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrar(String email, String rawPassword, Rol rol) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        String passwordHasheada = passwordEncoder.encode(rawPassword);
        
        Usuario nuevoUsuario = Usuario.builder()
                .email(email)
                .passwordHasheada(passwordHasheada)
                .rol(rol)
                .activo(true) // Se asume activo al registrar desde admin
                .build();

        return usuarioRepository.save(nuevoUsuario);
    }
}
