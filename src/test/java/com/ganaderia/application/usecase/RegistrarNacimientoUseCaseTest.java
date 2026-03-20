package com.ganaderia.application.usecase;

import com.ganaderia.application.port.in.RegistrarNacimientoCommand;
import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.domain.exception.DominioException;
import com.ganaderia.domain.model.EstadoProductivo;
import com.ganaderia.domain.model.IdVaca;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.repository.TerneroRepository;
import com.ganaderia.domain.repository.VacaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrarNacimientoUseCaseTest {

    private VacaRepository vacaRepository;
    private TerneroRepository terneroRepository;
    private EventPublisher eventPublisher;
    private RegistrarNacimientoUseCase useCase;

    @BeforeEach
    void setUp() {
        vacaRepository = mock(VacaRepository.class);
        terneroRepository = mock(TerneroRepository.class);
        eventPublisher = mock(EventPublisher.class);
        useCase = new RegistrarNacimientoUseCase(vacaRepository, terneroRepository, eventPublisher);
    }

    @Test
    @DisplayName("Debe registrar nacimiento, guardar aggregates y emitir eventos")
    void registrarNacimientoExitoso() {
        String idMadre = "vaca-123";
        LocalDate fechaParto = LocalDate.now().minusDays(10);
        RegistrarNacimientoCommand command = new RegistrarNacimientoCommand(idMadre, fechaParto, "MACHO");

        Vaca madre = new Vaca(new IdVaca(idMadre), "AR-001", LocalDate.now().minusYears(3));
        madre.consumirEventos(); // Limpiar eventos de creación

        when(vacaRepository.obtenerPorId(new IdVaca(idMadre))).thenReturn(madre);

        String idTerneroGenerado = useCase.ejecutar(command);

        assertNotNull(idTerneroGenerado);
        assertEquals(EstadoProductivo.EN_PRODUCCION, madre.getEstadoActual());
        assertNotNull(madre.getFechaUltimoParto());

        verify(vacaRepository).guardar(madre);
        verify(terneroRepository).guardar(any());
        verify(eventPublisher, times(2)).publicar(anyList()); // 1 de Vaca (PartoRegistrado) + 1 de Ternero (TerneroNacido)
    }

    @Test
    @DisplayName("Falla si la madre no existe")
    void fallaSiMadreNoExiste() {
        RegistrarNacimientoCommand command = new RegistrarNacimientoCommand("xyz", LocalDate.now(), "MACHO");
        when(vacaRepository.obtenerPorId(any())).thenReturn(null);

        assertThrows(DominioException.class, () -> useCase.ejecutar(command));
        verify(vacaRepository, never()).guardar(any());
        verify(terneroRepository, never()).guardar(any());
    }
}
