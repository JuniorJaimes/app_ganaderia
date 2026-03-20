package com.ganaderia.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA para persistir registros de ordeño en MySQL.
 */
@Entity
@Table(name = "registros_ordeno",
       uniqueConstraints = @UniqueConstraint(columnNames = {"vaca_id", "fecha", "turno"}))
public class RegistroOrdenoEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "vaca_id", nullable = false, length = 36)
    private String vacaId;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "turno", nullable = false)
    private String turno;

    @Column(name = "litros", nullable = false)
    private double litros;

    protected RegistroOrdenoEntity() {}

    public RegistroOrdenoEntity(String id, String vacaId, LocalDate fecha, String turno, double litros) {
        this.id = id;
        this.vacaId = vacaId;
        this.fecha = fecha;
        this.turno = turno;
        this.litros = litros;
    }

    public String getId() { return id; }
    public String getVacaId() { return vacaId; }
    public LocalDate getFecha() { return fecha; }
    public String getTurno() { return turno; }
    public double getLitros() { return litros; }
}
