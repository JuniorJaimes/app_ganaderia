package com.ganaderia.infrastructure.adapter.in.web;

import com.ganaderia.application.dto.RegistrarVacaDTO;
import com.ganaderia.application.usecase.RegistrarVacaUseCase;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.model.records.IdVaca;
import com.ganaderia.domain.repository.VacaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vacas")
public class VacaController {

    private final RegistrarVacaUseCase registrarVacaUseCase;
    private final VacaRepository vacaRepository;

    public VacaController(RegistrarVacaUseCase registrarVacaUseCase, VacaRepository vacaRepository) {
        this.registrarVacaUseCase = registrarVacaUseCase;
        this.vacaRepository = vacaRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registrar(@RequestBody RegistrarVacaDTO dto) {
        String id = registrarVacaUseCase.ejecutar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id, "mensaje", "Vaca registrada exitosamente"));
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarTodas() {
        List<Map<String, Object>> vacas = vacaRepository.listarTodas().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vacas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable String id) {
        Vaca vaca = vacaRepository.obtenerPorId(new IdVaca(id));
        return ResponseEntity.ok(toResponse(vaca));
    }

    private Map<String, Object> toResponse(Vaca vaca) {
        return Map.of(
                "id", vaca.getId().value(),
                "numeroArete", vaca.getNumeroArete(),
                "fechaNacimiento", vaca.getFechaNacimiento().toString(),
                "estadoActual", vaca.getEstadoActual().name(),
                "fechaUltimoParto", vaca.getFechaUltimoParto() != null ? vaca.getFechaUltimoParto().toString() : "N/A"
        );
    }
}
