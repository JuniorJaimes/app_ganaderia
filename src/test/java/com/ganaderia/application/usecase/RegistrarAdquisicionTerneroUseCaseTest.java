package com.ganaderia.application.usecase;

import com.ganaderia.application.port.in.RegistrarAdquisicionTerneroCommand;
import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.domain.repository.TerneroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrarAdquisicionTerneroUseCaseTest {

    private TerneroRepository terneroRepository;
    private EventPublisher eventPublisher;
    private RegistrarAdquisicionTerneroUseCase useCase;

    @BeforeEach
    void setUp() {
        terneroRepository = mock(TerneroRepository.class);
        eventPublisher = mock(EventPublisher.class);
        useCase = new RegistrarAdquisicionTerneroUseCase(terneroRepository, eventPublisher);
    }

    @Test
    @DisplayName("Debe registrar adquisición, guardar ternero y emitir evento")
    void registrarAdquisicionExitosa() {
        RegistrarAdquisicionTerneroCommand command = new RegistrarAdquisicionTerneroCommand(
                LocalDate.now().minusMonths(2), "HEMBRA"
        );

        String idGenerado = useCase.ejecutar(command);

        assertNotNull(idGenerado);
        verify(terneroRepository).guardar(any());
        verify(eventPublisher, times(1)).publicar(anyList()); // 1 evento de TerneroAdquirido
    }
}
