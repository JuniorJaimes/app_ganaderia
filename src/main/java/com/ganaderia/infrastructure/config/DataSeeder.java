package com.ganaderia.infrastructure.config;

import com.ganaderia.application.port.out.PasswordEncoderPort;
import com.ganaderia.application.port.out.UsuarioRepositoryPort;
import com.ganaderia.domain.model.Rol;
import com.ganaderia.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

   @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepositoryPort userRepository, PasswordEncoderPort passwordEncoder) {
        return args -> {
            String email = adminEmail;
            
            if (userRepository.findByEmail(email).isEmpty()) {
                Usuario admin = Usuario.builder()
                        .email(email)
                        .passwordHasheada(passwordEncoder.encode(adminPassword))
                        .rol(Rol.ADMIN)
                        .activo(true)
                        .build();
                
                userRepository.save(admin);
                System.out.println(">>> Usuario de prueba creado: " +adminEmail);
            } else {
                System.out.println(">>> El usuario de prueba ya existe.");
            }
        };
    }
}
