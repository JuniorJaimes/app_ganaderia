package com.ganaderia.domain.model;

import com.ganaderia.domain.exception.DominioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios del Agregado Vaca.
 * Puro Java + JUnit. Sin Spring. Sin BD. Ejecutan en milisegundos.
 */
class VacaTest {

    private Vaca crearVacaBase() {
        return new Vaca(
                new IdVaca("vaca-001"),
                "AR-001",
                LocalDate.of(2020, 1, 15)
        );
    }

    @Test
    @DisplayName("Una vaca nueva se registra en estado SECA")
    void vacaNuevaEsSeca() {
        Vaca vaca = crearVacaBase();
        assertEquals(EstadoProductivo.SECA, vaca.getEstadoActual());
    }

    @Test
    @DisplayName("Registrar parto cambia el estado a EN_PRODUCCION")
    void registrarPartoCambiaEstado() {
        Vaca vaca = crearVacaBase();
        vaca.registrarParto(LocalDate.of(2023, 6, 1));
        assertEquals(EstadoProductivo.EN_PRODUCCION, vaca.getEstadoActual());
    }

    @Test
    @DisplayName("Registrar parto retorna un idTernero no nulo")
    void registrarPartoRetornaIdTernero() {
        Vaca vaca = crearVacaBase();
        String idTernero = vaca.registrarParto(LocalDate.of(2023, 6, 1));
        assertNotNull(idTernero);
        assertFalse(idTernero.isBlank());
    }

    @Test
    @DisplayName("No se permite un parto antes de 10 meses del último")
    void invariantePeriodoGestacion() {
        Vaca vaca = crearVacaBase();
        vaca.registrarParto(LocalDate.of(2023, 1, 1));

        DominioException ex = assertThrows(DominioException.class, () ->
                vaca.registrarParto(LocalDate.of(2023, 6, 1)) // Solo 5 meses despues
        );
        assertTrue(ex.getMessage().contains("Invariante biológico"));
    }

    @Test
    @DisplayName("Se permite un segundo parto después de 10 meses")
    void segundoPartoPermitidoDespuesDe10Meses() {
        Vaca vaca = crearVacaBase();
        vaca.registrarParto(LocalDate.of(2022, 1, 1));
        vaca.secar(LocalDate.of(2022, 8, 1));

        assertDoesNotThrow(() ->
                vaca.registrarParto(LocalDate.of(2022, 12, 1)) // 11 meses después
        );
    }

    @Test
    @DisplayName("Secar una vaca ya seca lanza excepción")
    void secarVacaYaSecaLanzaExcepcion() {
        Vaca vaca = crearVacaBase();

        DominioException ex = assertThrows(DominioException.class, () ->
                vaca.secar(LocalDate.now())
        );
        assertTrue(ex.getMessage().contains("ya se encuentra seca"));
    }

    @Test
    @DisplayName("Una vaca seca NO puede ser ordeñada")
    void vacaSecaNoPuedeSerOrdenada() {
        Vaca vaca = crearVacaBase();
        assertFalse(vaca.puedeSerOrdenada());
    }

    @Test
    @DisplayName("Una vaca en producción SÍ puede ser ordeñada")
    void vacaEnProduccionPuedeSerOrdenada() {
        Vaca vaca = crearVacaBase();
        vaca.registrarParto(LocalDate.of(2023, 6, 1));
        assertTrue(vaca.puedeSerOrdenada());
    }

    @Test
    @DisplayName("Los eventos se generan y se consumen correctamente")
    void eventosSeGeneranYConsumen() {
        Vaca vaca = crearVacaBase();             // Genera VacaRegistradaEvent
        vaca.registrarParto(LocalDate.of(2023, 6, 1)); // Genera PartoRegistradoEvent

        var eventos = vaca.consumirEventos();
        assertEquals(2, eventos.size());

        // Después de consumir, la lista queda vacía
        assertTrue(vaca.consumirEventos().isEmpty());
    }

    @Test
    @DisplayName("No se permite crear vaca con arete vacío")
    void areteVacioLanzaExcepcion() {
        assertThrows(DominioException.class, () ->
                new Vaca(new IdVaca("v-1"), "", LocalDate.of(2020, 1, 1))
        );
    }

    @Test
    @DisplayName("No se permite fecha de nacimiento futura")
    void fechaNacimientoFuturaLanzaExcepcion() {
        assertThrows(DominioException.class, () ->
                new Vaca(new IdVaca("v-1"), "AR-X", LocalDate.now().plusDays(1))
        );
    }
}
