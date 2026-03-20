package com.ganaderia.infrastructure.config;

import com.ganaderia.application.port.out.PasswordEncoderPort;
import com.ganaderia.application.port.out.UsuarioRepositoryPort;
import com.ganaderia.domain.model.Rol;
import com.ganaderia.domain.model.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepositoryPort userRepository, PasswordEncoderPort passwordEncoder) {
        return args -> {
            String email = "test@ganaderia.com";
            
            if (userRepository.findByEmail(email).isEmpty()) {
                Usuario admin = Usuario.builder()
                        .email(email)
                        .passwordHasheada(passwordEncoder.encode("password123"))
                        .rol(Rol.ADMIN)
                        .activo(true)
                        .build();
                
                userRepository.save(admin);
                System.out.println(">>> Usuario de prueba creado: " + email + " / password123");
            } else {
                System.out.println(">>> El usuario de prueba ya existe.");
            }
        };
    }
}
