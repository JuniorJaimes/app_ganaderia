package com.ganaderia.application.port.in;

import com.ganaderia.domain.model.enums.Rol;
import com.ganaderia.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {
    Usuario registrar(String email, String rawPassword, Rol rol);
}
