package com.ganaderia.infrastructure.adapter.in.web;

import com.ganaderia.application.port.in.RegistrarTratamientoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/vacas/{vacaId}/salud")
public class SaludController {

    private final RegistrarTratamientoUseCase registrarTratamientoUseCase;

    public SaludController(RegistrarTratamientoUseCase registrarTratamientoUseCase) {
        this.registrarTratamientoUseCase = registrarTratamientoUseCase;
    }

    @PostMapping("/tratamiento")
    public ResponseEntity<Map<String, String>> registrarTratamiento(
            @PathVariable String vacaId,
            @RequestBody TratamientoRequest request) {
        registrarTratamientoUseCase.ejecutar(
                vacaId,
                request.medicamento(),
                request.diagnostico(),
                request.fechaAplicacion(),
                request.fechaFinRetiro(),
                request.observaciones()
        );
        return ResponseEntity.ok(Map.of("mensaje", "Tratamiento registrado exitosamente"));
    }

    // ── DTO interno ──

    public record TratamientoRequest(
            String medicamento,
            String diagnostico,
            LocalDate fechaAplicacion,
            LocalDate fechaFinRetiro,
            String observaciones
    ) {}
}
