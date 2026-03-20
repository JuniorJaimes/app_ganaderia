package com.ganaderia.infrastructure.adapter.in.web;

import com.ganaderia.application.port.in.ConfirmarPrenezUseCase;
import com.ganaderia.application.port.in.RegistrarCeloUseCase;
import com.ganaderia.application.port.in.RegistrarInseminacionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/vacas/{vacaId}/reproduccion")
public class ReproduccionController {

    private final RegistrarCeloUseCase registrarCeloUseCase;
    private final RegistrarInseminacionUseCase registrarInseminacionUseCase;
    private final ConfirmarPrenezUseCase confirmarPrenezUseCase;

    public ReproduccionController(RegistrarCeloUseCase registrarCeloUseCase,
                                  RegistrarInseminacionUseCase registrarInseminacionUseCase,
                                  ConfirmarPrenezUseCase confirmarPrenezUseCase) {
        this.registrarCeloUseCase = registrarCeloUseCase;
        this.registrarInseminacionUseCase = registrarInseminacionUseCase;
        this.confirmarPrenezUseCase = confirmarPrenezUseCase;
    }

    @PostMapping("/celo")
    public ResponseEntity<Map<String, String>> registrarCelo(
            @PathVariable String vacaId,
            @RequestBody CeloRequest request) {
        registrarCeloUseCase.ejecutar(vacaId, request.fecha(), request.observaciones());
        return ResponseEntity.ok(Map.of("mensaje", "Celo registrado exitosamente"));
    }

    @PostMapping("/inseminacion")
    public ResponseEntity<Map<String, String>> registrarInseminacion(
            @PathVariable String vacaId,
            @RequestBody InseminacionRequest request) {
        registrarInseminacionUseCase.ejecutar(vacaId, request.fecha(), request.toroId(), request.observaciones());
        return ResponseEntity.ok(Map.of("mensaje", "Inseminación registrada exitosamente"));
    }

    @PostMapping("/prenez")
    public ResponseEntity<Map<String, String>> confirmarPrenez(
            @PathVariable String vacaId,
            @RequestBody PrenezRequest request) {
        confirmarPrenezUseCase.ejecutar(vacaId, request.fecha(), request.observaciones());
        return ResponseEntity.ok(Map.of("mensaje", "Preñez confirmada exitosamente"));
    }

    // ── DTOs internos ──

    public record CeloRequest(LocalDate fecha, String observaciones) {}
    public record InseminacionRequest(LocalDate fecha, String toroId, String observaciones) {}
    public record PrenezRequest(LocalDate fecha, String observaciones) {}
}
