package com.ganaderia.domain.model;

import com.ganaderia.domain.event.*;
import com.ganaderia.domain.exception.DominioException;
import com.ganaderia.domain.model.enums.EstadoProductivo;
import com.ganaderia.domain.model.enums.EstadoReproductivo;
import com.ganaderia.domain.model.enums.EstadoSalud;
import com.ganaderia.domain.model.records.IdVaca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate Root: Vaca.
 * Controla su ciclo productivo (lactancia/seca), reproductivo (celo/inseminación/preñez)
 * y de salud (tratamientos veterinarios con períodos de retiro).
 * No tiene dependencia de ningún framework externo.
 */
public class Vaca {

    private static final int MESES_MINIMOS_ENTRE_PARTOS = 10;
    private static final int EDAD_MINIMA_PRIMER_PARTO_MESES = 18;

    private final IdVaca id;
    private final String numeroArete;
    private final LocalDate fechaNacimiento;
    private EstadoProductivo estadoActual;
    private EstadoReproductivo estadoReproductivo;
    private EstadoSalud estadoSalud;
    private LocalDate fechaUltimoParto;
    private LocalDate fechaUltimaInseminacion;

    private final List<EventoReproductivo> historialReproductivo = new ArrayList<>();
    private final List<Tratamiento> tratamientos = new ArrayList<>();
    private final List<Object> eventosDominio = new ArrayList<>();


    // Constructor principal (nuevo registro)


    public Vaca(IdVaca id, String numeroArete, LocalDate fechaNacimiento) {
        if (numeroArete == null || numeroArete.isBlank()) {
            throw new DominioException("El número de arete no puede estar vacío");
        }
        if (fechaNacimiento == null) {
            throw new DominioException("La fecha de nacimiento es obligatoria");
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new DominioException("La fecha de nacimiento no puede ser futura");
        }

        this.id = id;
        this.numeroArete = numeroArete;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoActual = EstadoProductivo.SECA;
        this.estadoReproductivo = EstadoReproductivo.VACIA;
        this.estadoSalud = EstadoSalud.SANA;
        this.fechaUltimaInseminacion = null;

        this.eventosDominio.add(new VacaRegistradaEvent(id.value(), numeroArete));
    }


    // Constructor de reconstitución (desde base de datos)
    // No emite eventos. La infraestructura lo usa para rehidratar.


    public static Vaca reconstituir(IdVaca id, String numeroArete, LocalDate fechaNacimiento,
                                     EstadoProductivo estado, LocalDate fechaUltimoParto) {
        return new Vaca(id, numeroArete, fechaNacimiento, estado, fechaUltimoParto);
    }

    public static Vaca reconstituir(IdVaca id, String numeroArete, LocalDate fechaNacimiento,
                                     EstadoProductivo estado, EstadoReproductivo estadoReproductivo,
                                     EstadoSalud estadoSalud, LocalDate fechaUltimoParto,
                                     LocalDate fechaUltimaInseminacion,
                                     List<EventoReproductivo> historialReproductivo,
                                     List<Tratamiento> tratamientos) {
        Vaca vaca = new Vaca(id, numeroArete, fechaNacimiento, estado, fechaUltimoParto);
        vaca.estadoReproductivo = estadoReproductivo != null ? estadoReproductivo : EstadoReproductivo.VACIA;
        vaca.estadoSalud = estadoSalud != null ? estadoSalud : EstadoSalud.SANA;
        vaca.fechaUltimaInseminacion = fechaUltimaInseminacion;
        if (historialReproductivo != null) {
            vaca.historialReproductivo.addAll(historialReproductivo);
        }
        if (tratamientos != null) {
            vaca.tratamientos.addAll(tratamientos);
        }
        return vaca;
    }

    private Vaca(IdVaca id, String numeroArete, LocalDate fechaNacimiento,
                 EstadoProductivo estado, LocalDate fechaUltimoParto) {
        this.id = id;
        this.numeroArete = numeroArete;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoActual = estado;
        this.estadoReproductivo = EstadoReproductivo.VACIA;
        this.estadoSalud = EstadoSalud.SANA;
        this.fechaUltimoParto = fechaUltimoParto;
        this.fechaUltimaInseminacion = null;
    }


    // Comportamiento 1: Registrar Parto
    // Invariantes: periodo mínimo entre partos, fecha coherente.
    // Efecto: cambia estado a EN_PRODUCCION, genera idTernero,
    //         resetea estado reproductivo a VACIA.


    public String registrarParto(LocalDate fechaParto) {
        if (fechaParto == null) {
            throw new DominioException("La fecha de parto es obligatoria");
        }
        if (fechaParto.isBefore(this.fechaNacimiento)) {
            throw new DominioException("La fecha de parto no puede ser anterior al nacimiento de la vaca");
        }
        LocalDate fechaMinimaPrimerParto = this.fechaNacimiento.plusMonths(EDAD_MINIMA_PRIMER_PARTO_MESES);

        if (fechaParto.isBefore(fechaMinimaPrimerParto)){
            throw new DominioException(
                    "Invariante biológico: La vaca es demasiado joven para parir. " +
                            "Debe tener al menos " + EDAD_MINIMA_PRIMER_PARTO_MESES + " meses. " +
                            "Fecha mínima permitida: " + fechaMinimaPrimerParto
            );
        }

        if (fechaUltimoParto != null
                && fechaParto.isBefore(fechaUltimoParto.plusMonths(MESES_MINIMOS_ENTRE_PARTOS))) {
            throw new DominioException(
                    "Invariante biológico: deben pasar al menos " + MESES_MINIMOS_ENTRE_PARTOS
                            + " meses entre partos. Último parto: " + fechaUltimoParto
            );
        }

        this.fechaUltimoParto = fechaParto;
        this.estadoActual = EstadoProductivo.EN_PRODUCCION;
        this.estadoReproductivo = EstadoReproductivo.VACIA;

        String idTernero = UUID.randomUUID().toString();
        this.eventosDominio.add(new PartoRegistradoEvent(this.id.value(), fechaParto, idTernero));

        return idTernero;
    }


    // Comportamiento 2: Secar Vaca
    // Invariante: no se puede secar una vaca ya seca.


    public void secar(LocalDate fechaSecado) {
        if (fechaSecado == null) {
            throw new DominioException("La fecha de secado es obligatoria");
        }
        if (this.estadoActual == EstadoProductivo.SECA) {
            throw new DominioException("La vaca ya se encuentra seca");
        }

        this.estadoActual = EstadoProductivo.SECA;
        this.eventosDominio.add(new VacaSecadaEvent(this.id.value(), fechaSecado));
    }

    // ──────────────────────────────────────────────
    // Comportamiento 3: Registrar Celo Detectado
    // Marca a la vaca como candidata para inseminación.
    // ──────────────────────────────────────────────

    public void registrarCelo(LocalDate fecha, String observaciones) {
        if (fecha == null) {
            throw new DominioException("La fecha de detección de celo es obligatoria");
        }
        if (this.estadoReproductivo == EstadoReproductivo.CARGADA) {
            throw new DominioException("No se puede registrar celo en una vaca confirmada como cargada");
        }

        EventoReproductivo evento = new EventoReproductivo(
                EventoReproductivo.TipoEventoReproductivo.CELO_DETECTADO,
                fecha, observaciones, null
        );
        this.historialReproductivo.add(evento);
        this.eventosDominio.add(new CeloDetectadoEvent(this.id.value(), fecha));
    }

    // ──────────────────────────────────────────────
    // Comportamiento 4: Registrar Inseminación
    // Invariante: requiere un toroId válido.
    // Efecto: cambia estado reproductivo a INSEMINADA.
    // ──────────────────────────────────────────────

    public void registrarInseminacion(LocalDate fecha, String toroId, String observaciones) {
        if (fecha == null) {
            throw new DominioException("La fecha de inseminación es obligatoria");
        }
        if (this.estadoReproductivo == EstadoReproductivo.CARGADA) {
            throw new DominioException("No se puede inseminar una vaca confirmada como cargada");
        }

        EventoReproductivo evento = new EventoReproductivo(
                EventoReproductivo.TipoEventoReproductivo.INSEMINACION,
                fecha, observaciones, toroId
        );
        this.historialReproductivo.add(evento);
        this.estadoReproductivo = EstadoReproductivo.INSEMINADA;
        this.fechaUltimaInseminacion = fecha;
        this.eventosDominio.add(new InseminacionRegistradaEvent(this.id.value(), fecha, toroId));
    }

    // ──────────────────────────────────────────────
    // Comportamiento 5: Confirmar Preñez (Palpación Positiva)
    // Invariante: debe haber sido inseminada previamente.
    // Efecto: cambia estado reproductivo a CARGADA.
    // ──────────────────────────────────────────────

    public void confirmarPrenez(LocalDate fecha, String observaciones) {
        if (fecha == null) {
            throw new DominioException("La fecha de confirmación de preñez es obligatoria");
        }
        if (this.estadoReproductivo != EstadoReproductivo.INSEMINADA
                && this.estadoReproductivo != EstadoReproductivo.DUDOSA) {
            throw new DominioException(
                    "No se puede confirmar preñez sin inseminación previa. Estado actual: " + this.estadoReproductivo
            );
        }

        EventoReproductivo evento = new EventoReproductivo(
                EventoReproductivo.TipoEventoReproductivo.PALPACION_POSITIVA,
                fecha, observaciones, null
        );
        this.historialReproductivo.add(evento);
        this.estadoReproductivo = EstadoReproductivo.CARGADA;
        this.eventosDominio.add(new PrenezConfirmadaEvent(this.id.value(), fecha));
    }

    // ──────────────────────────────────────────────
    // Comportamiento 6: Registrar Palpación Negativa
    // Efecto: la vaca vuelve a VACIA.
    // ──────────────────────────────────────────────

    public void registrarPalpacionNegativa(LocalDate fecha, String observaciones) {
        if (fecha == null) {
            throw new DominioException("La fecha de palpación es obligatoria");
        }

        EventoReproductivo evento = new EventoReproductivo(
                EventoReproductivo.TipoEventoReproductivo.PALPACION_NEGATIVA,
                fecha, observaciones, null
        );
        this.historialReproductivo.add(evento);
        this.estadoReproductivo = EstadoReproductivo.VACIA;
    }

    // ──────────────────────────────────────────────
    // Comportamiento 7: Registrar Palpación Dudosa
    // Efecto: requiere revisión posterior.
    // ──────────────────────────────────────────────

    public void registrarPalpacionDudosa(LocalDate fecha, String observaciones) {
        if (fecha == null) {
            throw new DominioException("La fecha de palpación es obligatoria");
        }

        EventoReproductivo evento = new EventoReproductivo(
                EventoReproductivo.TipoEventoReproductivo.PALPACION_DUDOSA,
                fecha, observaciones, null
        );
        this.historialReproductivo.add(evento);
        this.estadoReproductivo = EstadoReproductivo.DUDOSA;
    }

    // ──────────────────────────────────────────────
    // Comportamiento 8: Registrar Tratamiento Veterinario
    // Efecto: agrega tratamiento y cambia estado de salud
    //         según si hay periodo de retiro activo.
    // ──────────────────────────────────────────────

    public void registrarTratamiento(String medicamento, String diagnostico,
                                     LocalDate fechaAplicacion, LocalDate fechaFinRetiro,
                                     String observaciones) {
        Tratamiento tratamiento = new Tratamiento(
                medicamento, diagnostico, fechaAplicacion, fechaFinRetiro, observaciones
        );
        this.tratamientos.add(tratamiento);

        if (tratamiento.enPeriodoDeRetiro()) {
            this.estadoSalud = EstadoSalud.EN_TRATAMIENTO;
        }

        this.eventosDominio.add(new TratamientoRegistradoEvent(
                this.id.value(), medicamento, diagnostico, fechaAplicacion, fechaFinRetiro
        ));
    }

    // ──────────────────────────────────────────────
    // Consultas de Estado
    // ──────────────────────────────────────────────

    /**
     * Una vaca puede ser ordeñada si está en producción y su leche es apta.
     */
    public boolean puedeSerOrdenada() {
        return this.estadoActual == EstadoProductivo.EN_PRODUCCION;
    }

    /**
     * Determina si la leche de esta vaca es apta para el tanque general.
     * Revisa si hay algún tratamiento con periodo de retiro activo.
     */
    public boolean lecheAptaParaVenta() {
        return this.tratamientos.
                stream().noneMatch(Tratamiento::enPeriodoDeRetiro);
    }

    /**
     * Indica si la vaca lleva más de ciertos días "vacía" después de su último parto.
     * Util para generar alertas de fertilidad.
     */
    public boolean requiereAtencionReproductiva(int diasLimite) {
        if (this.estadoReproductivo != EstadoReproductivo.VACIA || this.fechaUltimoParto == null) {
            return false;
        }
        long diasDesdeUltimoParto = java.time.temporal.ChronoUnit.DAYS.between(
                this.fechaUltimoParto, LocalDate.now()
        );
        return diasDesdeUltimoParto > diasLimite;
    }

    // ──────────────────────────────────────────────
    // Extracción de eventos (la infraestructura los consume)
    // ──────────────────────────────────────────────

    public List<Object> consumirEventos() {
        List<Object> eventos = List.copyOf(this.eventosDominio);
        this.eventosDominio.clear();
        return eventos;
    }


    // Getters

    public IdVaca getId() { return id; }
    public String getNumeroArete() { return numeroArete; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public EstadoProductivo getEstadoActual() { return estadoActual; }
    public EstadoReproductivo getEstadoReproductivo() { return estadoReproductivo; }
    public EstadoSalud getEstadoSalud() { return estadoSalud; }
    public LocalDate getFechaUltimoParto() { return fechaUltimoParto; }
    public LocalDate getFechaUltimaInseminacion() { return fechaUltimaInseminacion; }
    public List<EventoReproductivo> getHistorialReproductivo() {
        return Collections.unmodifiableList(historialReproductivo);
    }
    public List<Tratamiento> getTratamientos() {
        return Collections.unmodifiableList(tratamientos);
    }
    public static final int DIAS_GESTACION = 283;
}
