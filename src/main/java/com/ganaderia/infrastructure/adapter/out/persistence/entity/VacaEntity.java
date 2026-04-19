package com.ganaderia.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA para persistir vacas en MySQL.
 * Esta clase SOLO vive en infraestructura.
 * Se mapea hacia/desde el modelo de dominio mediante un Mapper.
 */
@Entity
@Table(name = "vacas")
public class VacaEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "numero_arete", nullable = false, unique = true)
    private String numeroArete;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "estado_actual", nullable = false)
    private String estadoActual;

    @Column(name = "fecha_ultimo_parto")
    private LocalDate fechaUltimoParto;

    @Column(name = "estado_reproductivo", nullable = false)
    private String estadoReproductivo;

    @Column(name = "fecha_ultima_inseminacion")
    private LocalDate fechaUltimaInseminacion;

    // JPA requiere constructor vacío
    protected VacaEntity() {}

    public VacaEntity(String id, String numeroArete, LocalDate fechaNacimiento,
                      String estadoActual, LocalDate fechaUltimoParto,
                      String estadoReproductivo, LocalDate fechaUltimaInseminacion) {
        this.id = id;
        this.numeroArete = numeroArete;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoActual = estadoActual;
        this.fechaUltimoParto = fechaUltimoParto;
        this.estadoReproductivo = estadoReproductivo;
        this.fechaUltimaInseminacion = fechaUltimaInseminacion;
    }

    public String getId() { return id; }
    public String getNumeroArete() { return numeroArete; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getEstadoActual() { return estadoActual; }
    public LocalDate getFechaUltimoParto() { return fechaUltimoParto; }
    public String getEstadoReproductivo() { return estadoReproductivo; }
    public LocalDate getFechaUltimaInseminacion() { return fechaUltimaInseminacion; }
}
