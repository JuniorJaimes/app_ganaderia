package com.ganaderia.application.usecase;

import com.ganaderia.application.port.in.AutenticarUsuarioUseCase;
import com.ganaderia.application.port.out.PasswordEncoderPort;
import com.ganaderia.application.port.out.TokenProviderPort;
import com.ganaderia.application.port.out.UsuarioRepositoryPort;
import com.ganaderia.domain.model.Usuario;

public class AutenticarUsuarioUseCaseImpl implements AutenticarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenProviderPort tokenProviderPort;

    public AutenticarUsuarioUseCaseImpl(UsuarioRepositoryPort usuarioRepositoryPort, 
                                        PasswordEncoderPort passwordEncoderPort, 
                                        TokenProviderPort tokenProviderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public String autenticar(String email, String rawPassword) {
        Usuario usuario = usuarioRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!usuario.isActivo()) {
            throw new IllegalStateException("El usuario está inactivo");
        }

        if (!passwordEncoderPort.matches(rawPassword, usuario.getPasswordHasheada())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        return tokenProviderPort.generateToken(usuario);
    }
}
