package com.ganaderia.domain.model;

import com.ganaderia.domain.exception.DominioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios del Agregado RegistroOrdeno y Value Objects asociados.
 */
class RegistroOrdenoTest {

    private Vaca crearVacaEnProduccion() {
        Vaca vaca = new Vaca(new IdVaca("vaca-001"), "AR-001", LocalDate.of(2020, 1, 15));
        vaca.registrarParto(LocalDate.of(2023, 6, 1));
        vaca.consumirEventos(); // Limpiar eventos para tests limpios
        return vaca;
    }

    private Vaca crearVacaSeca() {
        return new Vaca(new IdVaca("vaca-002"), "AR-002", LocalDate.of(2020, 1, 15));
    }

    @Test
    @DisplayName("Se puede crear un ordeño con vaca en producción")
    void ordenoConVacaEnProduccion() {
        Vaca vaca = crearVacaEnProduccion();
        RegistroOrdeno registro = new RegistroOrdeno(
                "ord-001", vaca, LocalDate.now(), TurnoOrdeno.MANANA, new VolumenLeche(8.5)
        );
        assertNotNull(registro);
        assertEquals("ord-001", registro.getId());
    }

    @Test
    @DisplayName("NO se puede crear un ordeño con vaca SECA")
    void ordenoConVacaSecaLanzaExcepcion() {
        Vaca vaca = crearVacaSeca();
        assertThrows(DominioException.class, () ->
                new RegistroOrdeno("ord-001", vaca, LocalDate.now(), TurnoOrdeno.MANANA, new VolumenLeche(8.5))
        );
    }

    @Test
    @DisplayName("VolumenLeche no acepta valores negativos")
    void volumenNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new VolumenLeche(-1.0));
    }

    @Test
    @DisplayName("VolumenLeche no acepta cero")
    void volumenCeroLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new VolumenLeche(0.0));
    }

    @Test
    @DisplayName("VolumenLeche no acepta valores mayores a 60L")
    void volumenExcesivoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new VolumenLeche(61.0));
    }

    @Test
    @DisplayName("VolumenLeche acepta valores válidos")
    void volumenValidoSeCrea() {
        VolumenLeche vol = new VolumenLeche(12.5);
        assertEquals(12.5, vol.litros());
    }

    @Test
    @DisplayName("IdVaca no acepta valores vacíos")
    void idVacaVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new IdVaca(""));
    }

    @Test
    @DisplayName("IdVaca no acepta nulos")
    void idVacaNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new IdVaca(null));
    }
}
