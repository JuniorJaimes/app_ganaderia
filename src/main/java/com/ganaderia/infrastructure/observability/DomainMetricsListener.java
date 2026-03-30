package com.ganaderia.infrastructure.observability;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DomainMetricsListener {

    private final MeterRegistry meterRegistry;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public DomainMetricsListener(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    // Intercepta de forma generica los eventos que publiquen en el registro
    @EventListener
    public void onDomainEvent(Object event) {
        // Ignoramos eventos de Spring, solo nos importan los de nuestro dominio
        String eventClassName = event.getClass().getName();
        if (eventClassName.startsWith("com.ganaderia.domain.event")) {
            String eventType = event.getClass().getSimpleName();
            log.debug("📊 Registrando metrica de negocio para evento: {}", eventType);
            
            meterRegistry.counter("ganaderia.domain.events", "type", eventType).increment();
        }
    }
}
