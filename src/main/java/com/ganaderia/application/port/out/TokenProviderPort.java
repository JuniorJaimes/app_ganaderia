package com.ganaderia.application.port.out;

import com.ganaderia.domain.model.Usuario;

public interface TokenProviderPort {
    String generateToken(Usuario usuario);
}
