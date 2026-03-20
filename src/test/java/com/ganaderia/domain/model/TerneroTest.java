package com.ganaderia.domain.model;

import com.ganaderia.domain.event.TerneroAdquiridoEvent;
import com.ganaderia.domain.event.TerneroNacidoEvent;
import com.ganaderia.domain.exception.DominioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TerneroTest {

    @Test
    @DisplayName("Nacimiento propio crea ternero correctamente y emite evento")
    void crearNacimientoPropio() {
        IdTernero id = new IdTernero("t-001");
        IdVaca madre = new IdVaca("vaca-001");
        LocalDate fecha = LocalDate.now().minusDays(1);

        Ternero ternero = Ternero.nacerDe(id, madre, fecha, Sexo.HEMBRA);

        assertEquals(id, ternero.getId());
        assertTrue(ternero.getIdMadre().isPresent());
        assertEquals(madre, ternero.getIdMadre().get());
        assertEquals(fecha, ternero.getFechaNacimiento());
        assertEquals(Sexo.HEMBRA, ternero.getSexo());
        assertEquals(TipoIngresoTernero.NACIMIENTO_PROPIO, ternero.getTipoIngreso());

        List<Object> eventos = ternero.consumirEventos();
        assertEquals(1, eventos.size());
        assertTrue(eventos.get(0) instanceof TerneroNacidoEvent);
    }

    @Test
    @DisplayName("Nacimiento propio sin madre lanza excepción")
    void crearNacimientoSinMadreLanzaExcepcion() {
        assertThrows(DominioException.class, () ->
                Ternero.nacerDe(new IdTernero("t-1"), null, LocalDate.now(), Sexo.MACHO)
        );
    }

    @Test
    @DisplayName("Adquisición crea ternero sin madre y emite evento distinto")
    void crearAdquisicionExterna() {
        IdTernero id = new IdTernero("t-002");
        LocalDate fecha = LocalDate.now().minusMonths(6);

        Ternero ternero = Ternero.adquirir(id, fecha, Sexo.MACHO);

        assertEquals(id, ternero.getId());
        assertTrue(ternero.getIdMadre().isEmpty());
        assertEquals(fecha, ternero.getFechaNacimiento());
        assertEquals(Sexo.MACHO, ternero.getSexo());
        assertEquals(TipoIngresoTernero.ADQUISICION_EXTERNA, ternero.getTipoIngreso());

        List<Object> eventos = ternero.consumirEventos();
        assertEquals(1, eventos.size());
        assertTrue(eventos.get(0) instanceof TerneroAdquiridoEvent);
    }

    @Test
    @DisplayName("No se puede crear ternero con fecha futura")
    void fechaFuturaLanzaExcepcion() {
        assertThrows(DominioException.class, () ->
                Ternero.adquirir(new IdTernero("t-3"), LocalDate.now().plusDays(2), Sexo.HEMBRA)
        );
    }
}
