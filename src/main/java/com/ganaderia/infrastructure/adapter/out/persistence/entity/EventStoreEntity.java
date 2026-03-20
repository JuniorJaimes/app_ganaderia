package com.ganaderia.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Tabla de eventos append-only (EventStore).
 * Historial inmutable: una vez insertado, nunca se modifica ni se borra.
 */
@Entity
@Table(name = "event_store")
public class EventStoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(name = "agregado_id", nullable = false, length = 36)
    private String agregadoId;

    @Column(name = "datos", nullable = false, columnDefinition = "TEXT")
    private String datos;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    protected EventStoreEntity() {}

    public EventStoreEntity(String tipoEvento, String agregadoId, String datos, LocalDateTime timestamp) {
        this.tipoEvento = tipoEvento;
        this.agregadoId = agregadoId;
        this.datos = datos;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public String getTipoEvento() { return tipoEvento; }
    public String getAgregadoId() { return agregadoId; }
    public String getDatos() { return datos; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
