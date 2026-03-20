package com.ganaderia.application.port.in;

public interface AutenticarUsuarioUseCase {
    String autenticar(String email, String rawPassword);
}
