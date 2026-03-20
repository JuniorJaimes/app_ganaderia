package com.ganaderia.infrastructure.adapter.out.event;

import com.ganaderia.application.port.out.EventPublisher;
import com.ganaderia.infrastructure.adapter.out.persistence.SpringDataEventStoreRepository;
import com.ganaderia.infrastructure.adapter.out.persistence.entity.EventStoreEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del EventPublisher.
 * Persiste cada evento enn la tabla event_store (append-only)
 * y opcionalmente lo loguea para trazabilidad inmediata.
 */
@Component
public class JpaEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(JpaEventPublisher.class);
    private final SpringDataEventStoreRepository eventStoreRepository;
    private final ObjectMapper objectMapper;

    public JpaEventPublisher(SpringDataEventStoreRepository eventStoreRepository, ObjectMapper objectMapper) {
        this.eventStoreRepository = eventStoreRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publicar(List<Object> eventos) {
        for (Object evento : eventos) {
            try {
                String tipo = evento.getClass().getSimpleName();
                String datos = objectMapper.writeValueAsString(evento);

                // Extraer el agregadoId del evento (todos tienen vacaId como primer campo)
                String agregadoId = objectMapper.readTree(datos).path("vacaId").asText("desconocido");

                EventStoreEntity entry = new EventStoreEntity(tipo, agregadoId, datos, LocalDateTime.now());
                eventStoreRepository.save(entry);

                log.info("Evento persistido: {} para agregado {}", tipo, agregadoId);
            } catch (Exception e) {
                log.error("Error al persistir evento: {}", evento.getClass().getSimpleName(), e);
            }
        }
    }
}
