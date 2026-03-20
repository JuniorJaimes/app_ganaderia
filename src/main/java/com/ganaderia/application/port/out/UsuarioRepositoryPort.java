package com.ganaderia.application.port.out;

import com.ganaderia.domain.model.Usuario;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Optional<Usuario> findByEmail(String email);
    Usuario save(Usuario usuario);
}
