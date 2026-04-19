package com.ganaderia.domain.model;

import com.ganaderia.domain.event.TerneroAdquiridoEvent;
import com.ganaderia.domain.event.TerneroNacidoEvent;
import com.ganaderia.domain.exception.DominioException;
import com.ganaderia.domain.model.enums.Sexo;
import com.ganaderia.domain.model.enums.TipoIngresoTernero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Aggregate Root: Ternero.
 * Representa una cría que nace en la finca o se adquiere externamente.
 */
public class Ternero {

    private final IdTernero id;
    private final IdVaca idMadre; // Puede ser null si es adquirido
    private final LocalDate fechaNacimiento;
    private final Sexo sexo;
    private final TipoIngresoTernero tipoIngreso;

    private final List<Object> eventosDominio = new ArrayList<>();


    // Constructores de Fábrica (Dominio)


    public static Ternero nacerDe(IdTernero id, IdVaca idMadre, LocalDate fechaNacimiento, Sexo sexo) {
        if (idMadre == null) {
            throw new DominioException("Para un nacimiento propio, el ID de la madre es obligatorio.");
        }
        return new Ternero(id, idMadre, fechaNacimiento, sexo, TipoIngresoTernero.NACIMIENTO_PROPIO);
    }

    public static Ternero adquirir(IdTernero id, LocalDate fechaNacimiento, Sexo sexo) {
        return new Ternero(id, null, fechaNacimiento, sexo, TipoIngresoTernero.ADQUISICION_EXTERNA);
    }

    private Ternero(IdTernero id, IdVaca idMadre, LocalDate fechaNacimiento, Sexo sexo, TipoIngresoTernero tipoIngreso) {
        if (id == null) {
            throw new DominioException("El id del ternero es obligatorio");
        }
        if (fechaNacimiento == null) {
            throw new DominioException("La fecha de nacimiento es obligatoria");
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new DominioException("La fecha de nacimiento no puede ser futura");
        }
        if (sexo == null) {
            throw new DominioException("El sexo del ternero es obligatorio");
        }

        this.id = id;
        this.idMadre = idMadre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.tipoIngreso = tipoIngreso;

        if (tipoIngreso == TipoIngresoTernero.NACIMIENTO_PROPIO) {
            this.eventosDominio.add(new TerneroNacidoEvent(
                    id.value(),
                    idMadre.value(),
                    fechaNacimiento,
                    sexo.name()
            ));
        } else {
            this.eventosDominio.add(new TerneroAdquiridoEvent(
                    id.value(),
                    fechaNacimiento,
                    sexo.name()
            ));
        }
    }


    // Constructor de reconstitución (desde base de datos)
    // No emite eventos. La infraestructura lo usa para rehidratar.


    public static Ternero reconstituir(IdTernero id, IdVaca idMadre, LocalDate fechaNacimiento, 
                                       Sexo sexo, TipoIngresoTernero tipoIngreso) {
        Ternero ternero = new Ternero(id, idMadre, fechaNacimiento, sexo, tipoIngreso, true);
        return ternero;
    }

    private Ternero(IdTernero id, IdVaca idMadre, LocalDate fechaNacimiento, 
                    Sexo sexo, TipoIngresoTernero tipoIngreso, boolean isReconstituir) {
        this.id = id;
        this.idMadre = idMadre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.tipoIngreso = tipoIngreso;
    }

    // ──────────────────────────────────────────────
    // Extracción de eventos (la infraestructura los consume)
    // ──────────────────────────────────────────────

    public List<Object> consumirEventos() {
        List<Object> eventos = List.copyOf(this.eventosDominio);
        this.eventosDominio.clear();
        return eventos;
    }

    // ──────────────────────────────────────────────
    // Getters (solo lectura — Setters PROHIBIDOS)
    // ──────────────────────────────────────────────

    public IdTernero getId() { return id; }
    public Optional<IdVaca> getIdMadre() { return Optional.ofNullable(idMadre); }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public Sexo getSexo() { return sexo; }
    public TipoIngresoTernero getTipoIngreso() { return tipoIngreso; }
}
