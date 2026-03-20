package com.ganaderia.infrastructure.config;

import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.application.port.out.PasswordEncoderPort;
import com.ganaderia.application.port.out.TokenProviderPort;
import com.ganaderia.application.port.out.UsuarioRepositoryPort;
import com.ganaderia.application.usecase.*;
import com.ganaderia.domain.repository.RegistroOrdenoRepository;
import com.ganaderia.domain.repository.TerneroRepository;
import com.ganaderia.domain.repository.VacaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public AutenticarUsuarioUseCaseImpl autenticarUsuarioUseCase(
            UsuarioRepositoryPort usuarioRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenProviderPort tokenProviderPort) {
        return new AutenticarUsuarioUseCaseImpl(usuarioRepositoryPort, passwordEncoderPort, tokenProviderPort);
    }

    @Bean
    public RegistrarUsuarioUseCaseImpl registrarUsuarioUseCase(
            UsuarioRepositoryPort usuarioRepositoryPort,
            PasswordEncoderPort passwordEncoderPort) {
        return new RegistrarUsuarioUseCaseImpl(usuarioRepositoryPort, passwordEncoderPort);
    }

    @Bean
    public RegistrarVacaUseCase registrarVacaUseCase(VacaRepository vacaRepository, EventPublisher eventPublisher) {
        return new RegistrarVacaUseCase(vacaRepository, eventPublisher);
    }

    @Bean
    public RegistrarPartoUseCase registrarPartoUseCase(VacaRepository vacaRepository, EventPublisher eventPublisher) {
        return new RegistrarPartoUseCase(vacaRepository, eventPublisher);
    }

    @Bean
    public SecarVacaUseCase secarVacaUseCase(VacaRepository vacaRepository, EventPublisher eventPublisher) {
        return new SecarVacaUseCase(vacaRepository, eventPublisher);
    }

    @Bean
    public RegistrarNacimientoUseCase registrarNacimientoUseCase(VacaRepository vacaRepository, 
                                                                 TerneroRepository terneroRepository, 
                                                                 EventPublisher eventPublisher) {
        return new RegistrarNacimientoUseCase(vacaRepository, terneroRepository, eventPublisher);
    }

    @Bean
    public RegistrarAdquisicionTerneroUseCase registrarAdquisicionTerneroUseCase(TerneroRepository terneroRepository, 
                                                                                 EventPublisher eventPublisher) {
        return new RegistrarAdquisicionTerneroUseCase(terneroRepository, eventPublisher);
    }

    @Bean
    public RegistrarOrdenoUseCase registrarOrdenoUseCase(VacaRepository vacaRepository, 
                                                         RegistroOrdenoRepository ordenoRepository, 
                                                         EventPublisher eventPublisher) {
        return new RegistrarOrdenoUseCase(vacaRepository, ordenoRepository, eventPublisher);
    }

    @Bean
    public RegistrarCeloUseCaseImpl registrarCeloUseCase(VacaRepository vacaRepository, 
                                                          EventPublisher eventPublisher) {
        return new RegistrarCeloUseCaseImpl(vacaRepository, eventPublisher);
    }

    @Bean
    public RegistrarInseminacionUseCaseImpl registrarInseminacionUseCase(VacaRepository vacaRepository, 
                                                                         EventPublisher eventPublisher) {
        return new RegistrarInseminacionUseCaseImpl(vacaRepository, eventPublisher);
    }

    @Bean
    public ConfirmarPrenezUseCaseImpl confirmarPrenezUseCase(VacaRepository vacaRepository, 
                                                              EventPublisher eventPublisher) {
        return new ConfirmarPrenezUseCaseImpl(vacaRepository, eventPublisher);
    }

    @Bean
    public RegistrarTratamientoUseCaseImpl registrarTratamientoUseCase(VacaRepository vacaRepository, 
                                                                       EventPublisher eventPublisher) {
        return new RegistrarTratamientoUseCaseImpl(vacaRepository, eventPublisher);
    }
}
