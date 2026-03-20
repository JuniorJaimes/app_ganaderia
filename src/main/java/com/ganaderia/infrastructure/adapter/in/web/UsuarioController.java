package com.ganaderia.infrastructure.adapter.in.web;

import com.ganaderia.application.port.in.RegistrarUsuarioUseCase;
import com.ganaderia.domain.model.Usuario;
import com.ganaderia.infrastructure.adapter.in.web.dto.UsuarioRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UsuarioRequest request) {
        Usuario nuevoUsuario = registrarUsuarioUseCase.registrar(
                request.getEmail(),
                request.getPassword(),
                request.getRol()
        );
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }
}
